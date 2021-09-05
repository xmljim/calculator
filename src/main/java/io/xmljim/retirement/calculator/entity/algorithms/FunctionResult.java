package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.algorithms.model.util.Scalar;
import io.xmljim.retirement.calculator.entity.ServiceResult;

import java.util.List;

public interface FunctionResult extends ServiceResult {
    List<ParameterInfo> getParameters();

    String getFunctionName();

    Scalar getValue();
}
