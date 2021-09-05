package io.xmljim.retirement.calculator.controllers;

import io.xmljim.algorithms.functions.statistics.LinearRegressionModel;
import io.xmljim.algorithms.model.ScalarFunction;
import io.xmljim.retirement.calculator.entity.algorithms.FunctionResult;
import io.xmljim.retirement.calculator.entity.algorithms.FunctionResultImpl;
import io.xmljim.retirement.calculator.entity.algorithms.ModelResult;
import io.xmljim.retirement.calculator.entity.algorithms.ModelResultImpl;
import io.xmljim.retirement.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;

    @PostMapping("/mean")
    FunctionResult computeMean(@RequestBody List<Number> data) {
        ScalarFunction function = statisticsService.getMean(data);
        return new FunctionResultImpl(function);
    }

    @PostMapping("/sum")
    FunctionResult computeSum(@RequestBody List<Number> data) {
        ScalarFunction function = statisticsService.getSum(data);
        return new FunctionResultImpl(function);
    }

    @PostMapping("/median")
    FunctionResult computeMedian(@RequestBody List<Number> data) {
        ScalarFunction function = statisticsService.getMedian(data);
        return new FunctionResultImpl(function);
    }

    @PostMapping("/variance")
    FunctionResult computeVariance(@RequestBody List<Number> data) {
        ScalarFunction function = statisticsService.getVariance(data);
        return new FunctionResultImpl(function);
    }

    @PostMapping("/standardDeviation")
    FunctionResult computeStandardDeviation(@RequestBody List<Number> data) {
        ScalarFunction function = statisticsService.getStandardDeviation(data);
        return new FunctionResultImpl(function);
    }

    @PostMapping("/covariance")
    FunctionResult computeCovariance(@RequestBody Map<String, List<Number>> vectorMap) {
        ScalarFunction function = statisticsService.getCovariance(vectorMap.get("x"), vectorMap.get("y"));
        return new FunctionResultImpl(function);
    }

    @PostMapping("/linearRegression")
    ModelResult getLinearRegressionModel(@RequestBody Map<String, List<Number>> vectorMap) {
        LinearRegressionModel model = statisticsService.getLinearRegression(vectorMap.get("x"), vectorMap.get("y"));
        model.solve(); //needed to populate the model //TODO consider returning coefficients on .solve()
        return new ModelResultImpl(model);
    }
}
