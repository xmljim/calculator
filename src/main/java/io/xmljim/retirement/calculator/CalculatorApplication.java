package io.xmljim.retirement.calculator;

import io.xmljim.retirement.calculator.entity.ServiceResponse;
import io.xmljim.retirement.calculator.entity.stocks.ConsolidatedMarketHistory;
import io.xmljim.retirement.calculator.entity.stocks.MarketHistory;
import io.xmljim.retirement.calculator.entity.stocks.Market;
import io.xmljim.retirement.calculator.repository.StockMarketHistoryRepository;
import io.xmljim.retirement.service.StockMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CalculatorApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CalculatorApplication.class);

	@Autowired
	StockMarketHistoryRepository stockMarketRepository;

	@Autowired
	StockMarketService stockMarketService;

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@Override
	public void run (String... args) {
		logger.info("Starting Application...");

		System.out.println(stockMarketService.getMarketPrice(Market.DOW, 2021));

		List<MarketHistory> nasdaq = stockMarketService.getHistoryByMarket(Market.NASDAQ);
		System.out.println(nasdaq);

		List<ConsolidatedMarketHistory> consolidatedMarketHistories = stockMarketService.getConsolidatedMarketHistory(1991);
		System.out.println(consolidatedMarketHistories);
	}

}
