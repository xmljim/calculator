package io.xmljim.retirement.calculator.controllers;

import io.xmljim.retirement.calculator.entity.DefaultServiceResponse;
import io.xmljim.retirement.calculator.entity.ServiceResponse;
import io.xmljim.retirement.calculator.entity.ServiceResult;
import io.xmljim.retirement.calculator.entity.SimpleServiceItem;
import io.xmljim.retirement.calculator.entity.stocks.ConsolidatedMarketHistory;
import io.xmljim.retirement.calculator.entity.stocks.MarketHistory;
import io.xmljim.retirement.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stocks")
public class StockMarketController {

    @Autowired
    StockMarketService stockMarketService;

    @GetMapping("/")
    String sayHello() {
        return "Stocks";
    }

    @GetMapping("/market")
    ServiceResponse getMarkets() {
        List<String> markets = stockMarketService.getMarkets();
        List<ServiceResult> results = markets.stream().map(SimpleServiceItem<String>::new).collect(Collectors.toList());
        return new DefaultServiceResponse(results);
    }

    @GetMapping("/market/{market}")
    ServiceResponse getMarketHistory(@PathVariable("market") String market) {
        List<ServiceResult> results = new ArrayList<>(stockMarketService.getHistoryByMarket(market));
        return new DefaultServiceResponse(results);

    }

    @GetMapping("/market/{market}/{year}")
    ServiceResponse getMarketHistoryForYear(@PathVariable("year") int year, @PathVariable("market") String market) {
        Optional<MarketHistory> result = stockMarketService.getMarketPrice(market, year);
        return new DefaultServiceResponse(result.orElse(null));
    }

    @GetMapping("/market/consolidated")
    ServiceResponse getConsolidatedMarkets() {
        List<ServiceResult> consolidated = new ArrayList<>(stockMarketService.getConsolidatedMarketHistory());
        return new DefaultServiceResponse(consolidated);
    }

    @GetMapping("/market/consolidated/{year}")
    ServiceResponse getConsolidatedMarkets(@PathVariable("year") int fromYear) {
        List<ServiceResult> consolidated = new ArrayList<>(stockMarketService.getConsolidatedMarketHistory(fromYear));
        return new DefaultServiceResponse(consolidated);
    }

    @GetMapping("/market/consolidated/growth/{year}")
    ServiceResponse getConsolidatedMarketGrowthRate(@PathVariable("year") int fromYear) {
        SimpleServiceItem<Double> result = new SimpleServiceItem<>(stockMarketService.getConsolidatedMarketGrowthRate(fromYear));
        return new DefaultServiceResponse(result);
    }



}
