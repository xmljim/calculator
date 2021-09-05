package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.retirement.calculator.entity.ServiceResult;

import java.util.List;

public interface ParameterizedResult extends ServiceResult {
    List<ParameterInfo> getParameters();
}
