package io.xmljim.retirement.service;

import io.xmljim.algorithms.functions.financial.RetirementContributionModel;
import io.xmljim.algorithms.functions.financial.RetirementDistributionModel;
import io.xmljim.algorithms.functions.financial.RetirementModel;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.retirement.calculator.dto.RetirementInput;

public interface RetirementService {

    ScalarFunction getWeightedGrowthRate(double riskLevel);

    RetirementContributionModel getContributionModel(RetirementInput input);

    RetirementDistributionModel getDistributionModel(RetirementInput input);

    RetirementModel getRetirementModel(RetirementInput input);

    ScalarFunction amortize(RetirementInput input);
}
