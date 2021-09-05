package io.xmljim.retirement.calculator.entity.algorithms;

import io.xmljim.algorithms.model.Coefficient;
import io.xmljim.algorithms.model.Model;

import java.util.List;
import java.util.stream.Collectors;

public class ModelResultImpl extends AbstractParameterizedResult implements ModelResult {
    private final List<CoefficientInfo> coefficientList;
    private final String name;

    public ModelResultImpl(Model model) {
        super(model);
        this.name = model.getName();
        coefficientList = model.coefficients().map(CoefficientInfo::new).collect(Collectors.toList());
    }

    @Override
    public String getModelName() {
        return name;
    }

    @Override
    public List<CoefficientInfo> getCoefficients() {
        return coefficientList;
    }

    @Override
    public String getType() {
        return "ModelResult";
    }
}
