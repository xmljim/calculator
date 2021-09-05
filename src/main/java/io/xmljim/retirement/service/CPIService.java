package io.xmljim.retirement.service;

import io.xmljim.retirement.calculator.entity.stocks.CPI;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface CPIService {

    Optional<CPI> getCPIForYear(int year);

    List<CPI> getAll();

    List<CPI> getAllBetween(int startYear, int endYear);

    double computeInflationMultiplier(int forYear, int baseYear);

    default double getAverageInflation(int numberOfYears) {
        List<CPI> cpiData = getAll();

        List<CPI> data = cpiData.stream().sorted(Comparator.comparing(CPI::getYear).reversed()).collect(Collectors.toList());

        return IntStream.range(0, numberOfYears).mapToDouble(i -> {
            double rate = data.get(i).getCpiRate();
            double previousYear = data.get(i + 1).getCpiRate();

            return (rate - previousYear) / previousYear;
        }).average().orElse(0.0);
    }
}
