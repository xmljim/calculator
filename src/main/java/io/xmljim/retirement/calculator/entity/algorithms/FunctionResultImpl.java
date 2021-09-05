package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.algorithms.model.Function;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionResultImpl extends AbstractParameterizedResult implements FunctionResult {
    private String name;
    private List<ParameterInfo> parameterInfoList;
    Scalar value;

    public FunctionResultImpl(Function<?> function) {
        super(function);
        this.name = function.getName();
        this.value = (Scalar) function.compute();
    }

    @Override
    public String getType() {
        return "FunctionResult";
    }

    @Override
    public String getFunctionName() {
        return name;
    }

    @Override
    public Scalar getValue() {
        return value;
    }
}
