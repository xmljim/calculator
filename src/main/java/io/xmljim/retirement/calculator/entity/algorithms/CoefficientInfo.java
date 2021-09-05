package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.algorithms.model.Coefficient;
import lombok.Getter;

@Getter
public class CoefficientInfo {

    private String name;
    private String label;
    private Object value;

    public CoefficientInfo(Coefficient<?> coefficient) {
        this.name = coefficient.getName();
        this.label = coefficient.getLabel();
        this.value = coefficient.getValue();
    }
}
