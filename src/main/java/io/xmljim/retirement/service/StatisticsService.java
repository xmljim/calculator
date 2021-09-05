package io.xmljim.retirement.service;

import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.util.Scalar;

import java.util.List;

public interface StatisticsService {

    ScalarFunction getMean(List<Number> data);

    ScalarFunction getSum(List<Number> data);

    ScalarFunction getMedian(List<Number> data);

    ScalarFunction getVariance(List<Number> data);

    ScalarFunction getStandardDeviation(List<Number> data);

    ScalarFunction getCovariance(List<Number> data1, List<Number> data2);

    LinearRegressionModel getLinearRegression(List<Number> dataX, List<Number> dataY);
}
