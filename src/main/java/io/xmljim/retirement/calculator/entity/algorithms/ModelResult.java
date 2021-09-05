package io.xmljim.retirement.calculator.entity.algorithms;

import java.util.List;

public interface ModelResult extends ParameterizedResult {

    String getModelName();

    List<CoefficientInfo> getCoefficients();

}
