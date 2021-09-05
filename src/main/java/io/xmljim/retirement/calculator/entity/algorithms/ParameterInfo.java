package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.algorithms.model.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParameterInfo {

    private String name;
    private String variable;
    private String parameterType;
    private Object value;

    public ParameterInfo(Parameter<?> parameter) {
        setName(parameter.getName());
        setVariable(parameter.getVariable());
        setParameterType(parameter.getParameterType().toString());
        setValue(parameter.getValue());
    }
}
