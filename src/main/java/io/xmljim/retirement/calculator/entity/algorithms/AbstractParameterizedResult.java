package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.algorithms.model.Parameterized;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractParameterizedResult implements ParameterizedResult {
    private List<ParameterInfo> parameterInfoList;

    public AbstractParameterizedResult(Parameterized parameterized) {
        parameterInfoList = parameterized.stream()
                .map(param -> new ParameterInfo(param))
                .collect(Collectors.toList());
    }

    @Override
    public List<ParameterInfo> getParameters() {
        return parameterInfoList;
    }
}
