package io.xmljim.retirement.service;

import io.xmljim.retirement.calculator.entity.ServiceResponse;
import io.xmljim.retirement.calculator.entity.stocks.ConsolidatedMarketHistory;
import io.xmljim.retirement.calculator.entity.stocks.MarketHistory;
import io.xmljim.retirement.calculator.entity.stocks.Market;

import java.util.List;
import java.util.Optional;

public interface StockMarketService {

    Optional<MarketHistory> getMarketPrice(String market, int year);

    default Optional<MarketHistory> getMarketPrice(Market market, int year) {
        return getMarketPrice(market.getName(), year);
    }

    void addMarketHistory(MarketHistory marketHistory);

    List<MarketHistory> getHistoryByMarket(String market);

    default List<MarketHistory> getHistoryByMarket(Market market) {
        return getHistoryByMarket(market.getName());
    }

    List<MarketHistory> getMarketHistoryOnAfter(int year);

    List<MarketHistory> getMarketHistoryForMarketOnAfter(String market, int year);

    default List<MarketHistory> getMarketHistoryForMarketOnAfter(Market market, int year) {
        return getMarketHistoryForMarketOnAfter(market.getName(), year);
    }

    List<ConsolidatedMarketHistory> getConsolidatedMarketHistory();

    List<ConsolidatedMarketHistory> getConsolidatedMarketHistory(int fromYear);

    List<String> getMarkets();

    Double getConsolidatedMarketGrowthRate(int fromYear);

}
