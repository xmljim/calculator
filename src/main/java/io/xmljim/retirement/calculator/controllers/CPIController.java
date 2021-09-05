package io.xmljim.retirement.calculator.controllers;

import io.xmljim.retirement.calculator.entity.DefaultServiceResponse;
import io.xmljim.retirement.calculator.entity.ServiceResponse;
import io.xmljim.retirement.calculator.entity.ServiceResult;
import io.xmljim.retirement.calculator.entity.SimpleServiceItem;
import io.xmljim.retirement.calculator.entity.stocks.CPI;
import io.xmljim.retirement.service.CPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cpi")
public class CPIController {
    @Autowired
    CPIService cpiService;

    @GetMapping("/{year}")
    ServiceResponse getCPIForYear(@PathVariable("year") int year) {
        Optional<CPI> cpi = cpiService.getCPIForYear(year);
        return new DefaultServiceResponse(cpi.orElse(null));
    }

    @GetMapping("/")
    ServiceResponse listCPIHistory() {
        List<ServiceResult> results = new ArrayList<>(cpiService.getAll());
        return new DefaultServiceResponse(results);
    }

    @GetMapping("/{startYear}/{endYear}")
    ServiceResponse getCPIBetween(@PathVariable("startYear") int startYear, @PathVariable("endYear") int endYear) {
        List<ServiceResult> results = new ArrayList<>(cpiService.getAllBetween(startYear, endYear));
        return new DefaultServiceResponse(results);
    }

    @GetMapping("/inflation/{year}/{baseYear}")
    ServiceResponse getInflationMultiplier(@PathVariable("year") int year, @PathVariable("baseYear") int baseYear) {
        SimpleServiceItem<Double> result = new SimpleServiceItem<>(cpiService.computeInflationMultiplier(year, baseYear));
        return new DefaultServiceResponse(result);
    }

}
