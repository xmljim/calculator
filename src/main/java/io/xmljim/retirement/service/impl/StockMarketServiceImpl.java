package io.xmljim.retirement.service.impl;

import io.xmljim.algorithms.client.AlgorithmClient;
import io.xmljim.algorithms.client.ProviderNotAvailableException;
import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.util.Scalar;
import io.xmljim.retirement.calculator.entity.stocks.ConsolidatedMarketHistory;
import io.xmljim.retirement.calculator.entity.stocks.Market;
import io.xmljim.retirement.calculator.entity.stocks.MarketHistory;
import io.xmljim.retirement.calculator.repository.StockMarketHistoryRepository;
import io.xmljim.retirement.service.CPIService;
import io.xmljim.retirement.service.StatisticsService;
import io.xmljim.retirement.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StockMarketServiceImpl implements StockMarketService {

    @Autowired
    StockMarketHistoryRepository stockMarketHistoryRepository;

    @Autowired
    CPIService cpiService;

    @Autowired
    StatisticsService statisticsService;


    @Override
    public Optional<MarketHistory> getMarketPrice(final String market, final int year) {
        return stockMarketHistoryRepository.findByYearAndMarket(year, market);
    }

    @Override
    public void addMarketHistory(final MarketHistory marketHistory) {
        stockMarketHistoryRepository.save(marketHistory);
    }

    @Override
    public List<MarketHistory> getHistoryByMarket(final String market) {
        return stockMarketHistoryRepository.findByMarket(market);
    }


    @Override
    public List<MarketHistory> getMarketHistoryOnAfter(final int year) {
        return stockMarketHistoryRepository.findByYearGreaterThanEqual(year);
    }

    @Override
    public List<MarketHistory> getMarketHistoryForMarketOnAfter(final String market, final int year) {
        return stockMarketHistoryRepository.findByMarketAndYearGreaterThanEquals(year, market);
    }

    @Override
    public List<ConsolidatedMarketHistory> getConsolidatedMarketHistory() {
        List<MarketHistory> dow = getHistoryByMarket(Market.DOW);
        List<MarketHistory> nasdaq = getHistoryByMarket(Market.NASDAQ);
        List<MarketHistory> sp = getHistoryByMarket(Market.SP);

        return getConsolidatedMarketHistory(dow, nasdaq, sp);
    }

    @Override
    //TODO Set up caching;
    public List<ConsolidatedMarketHistory> getConsolidatedMarketHistory(final int fromYear) {
        List<MarketHistory> dow = getMarketHistoryForMarketOnAfter( Market.DOW, fromYear);
        List<MarketHistory> nasdaq = getMarketHistoryForMarketOnAfter( Market.NASDAQ, fromYear);
        List<MarketHistory> sp = getMarketHistoryForMarketOnAfter( Market.SP, fromYear);

        return getConsolidatedMarketHistory(dow, nasdaq, sp);
    }


    private List<ConsolidatedMarketHistory> getConsolidatedMarketHistory(List<MarketHistory>... markets) {
        //We'll make sure that we're only aggregating results for years that all markets have entries for
        //This ensures that we're creating consolidated history values that are comparable year over year
        //To do this, we need to find the earliest year value that are common to all of the markets
        //Similarly, we'll find the last year common to all of them.

        //short circuit if any of the markets is empty
        boolean isAnyMarketEmpty = Arrays.stream(markets).filter(List::isEmpty).count() > 0;
        if (isAnyMarketEmpty) {
            return Collections.emptyList();
        }

        int minValue  = Arrays.stream(markets)
                .mapToInt(market -> market.stream().mapToInt(m -> m.getYear()).min().orElse(0))
                .max().orElse(0);

        int maxValue = Arrays.stream(markets)
                .mapToInt(market -> market.stream().mapToInt(m -> m.getYear()).max().orElse(0))
                .min().orElse(0);

        //filter the lists to only include market histories that have the same minimum year in
        //all of the lists. This will ensure that we're consolidating for each consistently
        List<List<MarketHistory>> listValues = Arrays.stream(markets).map(list -> {
            return list.stream().sorted((a, b) -> a.getYear().compareTo(b.getYear()))
                    .filter(market -> market.getYear() >= minValue).collect(Collectors.toList());
        }).collect(Collectors.toList());

        //now we'll create a map that is keyed by year, and contains a set of MarketHistories
        //that we can aggregate against
        Map<Integer, Set<MarketHistory>> yearMarketMap = new LinkedHashMap<>();

        //collect the market history from each market and collect to a set of values for the given year
        IntStream.range(0, maxValue - minValue + 1).forEach(i -> {
            Set<MarketHistory> set = listValues.stream().map(marketList -> marketList.get(i)).collect(Collectors.toSet());
            yearMarketMap.put(minValue + i, set);
        });

        //since we're handling creation and aggregation in a lambda,
        //variables have be intrinsically be final.  Since these values
        //are volatile, Atomics all allow us to update the variables
        //without violating the lambda requirements...
        final  AtomicReference<Double> netChange = new AtomicReference<>(0.0);
        final AtomicReference<Double> previousClose = new AtomicReference<>(0.0);
        final AtomicReference<Double> baseStart = new AtomicReference<>(0.0);

        //create the final list of consolidated market histories. Each entry is keyed by
        //year, and the value is the set of market histories for each market for that year
        //With that we can create an aggregate of the values and calculate some additional
        //values needed for further analysis
        List<ConsolidatedMarketHistory> consolidated = yearMarketMap.entrySet().stream()
                .map(entry -> {
                    double yearOpen = entry.getValue().stream().mapToDouble(m -> m.getYearOpen()).sum();
                    double yearClose = entry.getValue().stream().mapToDouble(m -> m.getYearClose()).sum();
                    double yearNetChange = previousClose.get() == 0 ? yearClose - yearOpen : yearClose - previousClose.get();
                    double pctChange = previousClose.get() == 0 ? 100.0 : yearNetChange / previousClose.get();
                    //we'll aggregate the change in value using the first year's open as the base for everything
                    //if we're on the first entry, then we'll use the yearOpen, otherwise we'll calculate from
                    //the baseStart value
                    double aggregateChange = baseStart.get() == 0.0 ? yearClose - yearOpen : yearClose - baseStart.get();

                    //we'll adjust the aggregate change for inflation, using the last entry's year as the
                    //basis for evaluation.  So if the current year is 1995, and the last year in our list
                    //is 2020, then we'll adjust the value to 2020 dollars
                    double cpiMultiplier = cpiService.computeInflationMultiplier(entry.getKey(), maxValue);
                    double adjustedAggregateChange = aggregateChange * cpiMultiplier;

                    //calculate the log(base10) values of the aggregate change. This will allow
                    //for arriving at the _rate_ of change using a standard OLS regression.
                    //The rationale is that net change (growth) is exponential, i.e., the
                    //rate of change is a compound of previous change (like interest). So,
                    //using the log of the aggregate values gives us a set of points that
                    //can be evaluated in a linear equation like OLS. We can then use the
                    //slope coefficient to calculate the rate of change simply by converting
                    //it to 10^[slope] to get the best fit rate of change
                    double logAggregateChange = Math.log10(aggregateChange);
                    double logAdjustedAggregateChange = Math.log10(adjustedAggregateChange);

                    //update for the next iteration
                    previousClose.set(yearClose);
                    netChange.set(aggregateChange);
                    if (baseStart.get() == 0) {
                        baseStart.set(yearOpen);
                    }

                    //Create and return our results for the year
                    ConsolidatedMarketHistory cmh = new ConsolidatedMarketHistory();
                    cmh.setYear(entry.getKey());
                    cmh.setYearOpen(yearOpen);
                    cmh.setYearClose(yearClose);
                    cmh.setNetChange(yearNetChange);
                    cmh.setPctChange(pctChange);
                    cmh.setAggregateChange(aggregateChange);
                    cmh.setAdjustedAggregateChange(adjustedAggregateChange);
                    cmh.setLogAggregateChange(logAggregateChange);
                    cmh.setLogAdjustedAggregateChange(logAdjustedAggregateChange);

                    return cmh;
                }).collect(Collectors.toList());

        return consolidated;
    }

    @Override
    public Double getConsolidatedMarketGrowthRate(final int fromYear) {
        List<ConsolidatedMarketHistory> marketHistories = getConsolidatedMarketHistory(fromYear);
        return getRateOfChange(marketHistories);
    }

    private double getRateOfChange(List<ConsolidatedMarketHistory> consolidatedMarketHistories) {
        List<Number> years = new ArrayList<>();
        List<Number> logNetChange = new ArrayList<>();

        //pull the year and logAggregateChange values into separate lists.  These will be converted to
        //vectors when we run the regression
        consolidatedMarketHistories.forEach(cmh -> {
            if (cmh.getLogAggregateChange() != Double.NaN) {
                years.add(cmh.getYear());
                logNetChange.add(cmh.getLogAggregateChange());
            }
        });

        LinearRegressionModel regressionModel = statisticsService.getLinearRegression(years, logNetChange);
        regressionModel.solve();

        double slopeCoefficient = regressionModel.getSlope().asDouble();
        return Math.pow(10, slopeCoefficient) - 1;

    }

    @Override
    public List<String> getMarkets() {
        return stockMarketHistoryRepository.findDistinctMarkets();

    }
}
