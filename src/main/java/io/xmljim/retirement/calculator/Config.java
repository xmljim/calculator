package io.xmljim.retirement.calculator;

import io.xmljim.algorithms.client.AlgorithmClient;
import io.xmljim.algorithms.client.ProviderNotAvailableException;
import io.xmljim.retirement.service.CPIService;
import io.xmljim.retirement.service.RetirementService;
import io.xmljim.retirement.service.StatisticsService;
import io.xmljim.retirement.service.StockMarketService;
import io.xmljim.retirement.service.impl.CPIServiceImpl;
import io.xmljim.retirement.service.impl.RetirementServiceImpl;
import io.xmljim.retirement.service.impl.StatisticsServiceImpl;
import io.xmljim.retirement.service.impl.StockMarketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    static StockMarketService stockMarketService() {
        return new StockMarketServiceImpl();
    }

    @Bean CPIService cpiService() {
        return new CPIServiceImpl();
    }

    @Bean
    AlgorithmClient algorithmClient() throws ProviderNotAvailableException {
        return new AlgorithmClient();
    }

    @Bean
    StatisticsService statisticsService() {
        return new StatisticsServiceImpl();
    }

    @Bean
    RetirementService retirementService() {
        return new RetirementServiceImpl();
    }
}
