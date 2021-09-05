package io.xmljim.retirement.service.impl;

import io.xmljim.algorithms.client.AlgorithmClient;
import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.algorithms.model.ScalarVector;
import io.xmljim.algorithms.model.util.Scalar;
import io.xmljim.retirement.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    AlgorithmClient algorithmClient;

    @Override
    public ScalarFunction getMean(final List<Number> data) {
        ScalarVector vector = algorithmClient.getVectorFactory().createScalarVector("data", data);
        ScalarFunction meanFunction = algorithmClient.getStatistics().mean(vector);
        return meanFunction;
    }

    @Override
    public ScalarFunction getSum(final List<Number> data) {
        ScalarVector vector = algorithmClient.getVectorFactory().createScalarVector("data", data);
        return algorithmClient.getStatistics().sum(vector);
    }

    @Override
    public ScalarFunction getMedian(final List<Number> data) {
        ScalarVector vector = algorithmClient.getVectorFactory().createScalarVector("data", data);
        return algorithmClient.getStatistics().median(vector);
    }

    @Override
    public ScalarFunction getVariance(final List<Number> data) {
        ScalarVector vector = algorithmClient.getVectorFactory().createScalarVector("data", data);
        return algorithmClient.getStatistics().variance(vector);
    }

    @Override
    public ScalarFunction getStandardDeviation(final List<Number> data) {
        ScalarVector vector = algorithmClient.getVectorFactory().createScalarVector("data", data);
        return algorithmClient.getStatistics().standardDeviation(vector);
    }

    @Override
    public ScalarFunction getCovariance(final List<Number> data1, final List<Number> data2) {
        ScalarVector vectorX = algorithmClient.getVectorFactory().createScalarVector("data", "x", data1);
        ScalarVector vectorY = algorithmClient.getVectorFactory().createScalarVector("data", "y", data2);
        return algorithmClient.getStatistics().covariance(vectorX, vectorY);
    }

    @Override
    public LinearRegressionModel getLinearRegression(final List<Number> dataX, final List<Number> dataY) {
        ScalarVector vectorX = algorithmClient.getVectorFactory().createScalarVector("data", "x", dataX);
        ScalarVector vectorY = algorithmClient.getVectorFactory().createScalarVector("data", "y", dataY);
        LinearRegressionModel model = algorithmClient.getStatistics().linearRegression(vectorX, vectorY);
        return model;
    }
}
