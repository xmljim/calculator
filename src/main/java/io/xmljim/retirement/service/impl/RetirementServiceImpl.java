package io.xmljim.retirement.service.impl;

import io.xmljim.algorithms.client.AlgorithmClient;
import io.xmljim.algorithms.functions.financial.PaymentFrequency;
import io.xmljim.algorithms.functions.financial.RetirementContributionModel;
import io.xmljim.algorithms.functions.financial.RetirementDistributionModel;
import io.xmljim.algorithms.functions.financial.RetirementModel;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.retirement.calculator.dto.RetirementInput;
import io.xmljim.retirement.service.CPIService;
import io.xmljim.retirement.service.RetirementService;
import io.xmljim.retirement.service.StockMarketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class RetirementServiceImpl implements RetirementService {

    @Autowired
    AlgorithmClient algorithmClient;

    @Autowired
    StockMarketService stockMarketService;

    @Autowired
    CPIService cpiService;



    @Override
    public ScalarFunction getWeightedGrowthRate(final double riskLevel) {
        //FIXME create TreasuryYield service
        double stockGrowth = stockMarketService.getConsolidatedMarketGrowthRate(1991);
        return algorithmClient.getFinancial().weightedGrowth(stockGrowth, 0.013, riskLevel);

    }

    @Override
    public RetirementContributionModel getContributionModel(final RetirementInput input) {
        double weightedGrowth = getWeightedGrowthRate(input.getInvestmentStyle()).compute().asDouble();
        PaymentFrequency contributionFreqency = input.getContributionFrequency() != null ?
                PaymentFrequency.valueOf(input.getContributionFrequency().toUpperCase()) : PaymentFrequency.SEMI_MONTHLY;
        return algorithmClient.getFinancial().retirementContributionModel(input.getAge(), input.getRetirementAge(), input.getCurrentSalary(),
                input.getSelfContributionPct(), input.getEmployerContributionPct(), input.getCurrentRetirementBalance(), input.getColaPct(), weightedGrowth,
                contributionFreqency);
    }

    @Override
    public RetirementDistributionModel getDistributionModel(final RetirementInput input) {
        double inflationRate = cpiService.getAverageInflation(20);
        int retirementYear = LocalDate.now().getYear() + (input.getRetirementAge() - input.getAge());
        PaymentFrequency distributionFrequency =  PaymentFrequency.valueOf(input.getDistributionFrequency().toUpperCase());

        return algorithmClient.getFinancial().retirementDistributionModel(input.getCurrentRetirementBalance(), retirementYear, input.getPostRetirementInterestRate(),
                distributionFrequency, inflationRate, input.getRetirementDuration(), input.getAnnualizedDistribution());

    }

    @Override
    public RetirementModel getRetirementModel(final RetirementInput input) {
        double weightedGrowth = getWeightedGrowthRate(input.getInvestmentStyle()).compute().asDouble();
        double inflationRate = cpiService.getAverageInflation(20);
        PaymentFrequency distributionFrequency = input.getDistributionFrequency() != null ?
                PaymentFrequency.valueOf(input.getDistributionFrequency().toUpperCase()) : PaymentFrequency.MONTHLY;
        PaymentFrequency contributionFreqency = input.getContributionFrequency() != null ?
                PaymentFrequency.valueOf(input.getContributionFrequency().toUpperCase()) : PaymentFrequency.SEMI_MONTHLY;

        return algorithmClient.getFinancial().retirementModel(input.getAge(), input.getRetirementAge(), input.getCurrentSalary(), input.getCurrentRetirementBalance(),
                input.getSelfContributionPct(), input.getEmployerContributionPct(), input.getColaPct(), weightedGrowth, contributionFreqency, input.getPostRetirementInterestRate(),
                distributionFrequency, inflationRate, input.getRetirementDuration(), input.getIncomeReplacementPct());

    }

    @Override
    public ScalarFunction amortize(final RetirementInput input) {
        return null;
    }
}
