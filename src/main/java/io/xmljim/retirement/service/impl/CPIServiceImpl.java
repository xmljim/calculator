package io.xmljim.retirement.service.impl;

import io.xmljim.retirement.calculator.entity.DefaultServiceResponse;
import io.xmljim.retirement.calculator.entity.ServiceResult;
import io.xmljim.retirement.calculator.entity.stocks.CPI;
import io.xmljim.retirement.calculator.repository.CPIRepository;
import io.xmljim.retirement.service.CPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CPIServiceImpl implements CPIService {
    @Autowired
    CPIRepository cpiRepository;

    @Override
    public Optional<CPI> getCPIForYear(final int year) {
        return cpiRepository.findByYear(year);
    }

    @Override
    public double computeInflationMultiplier(final int forYear, final int baseYear) {
        Optional<CPI> yearCPI = getCPIForYear(forYear);
        Optional<CPI> baseCPI = getCPIForYear(baseYear);

        if (yearCPI.isEmpty() || baseCPI.isEmpty()) {
            return 0.0;
        }

        return baseCPI.get().getCpiRate() / yearCPI.get().getCpiRate();
    }

    @Override
    public List<CPI> getAll() {
        List<CPI> cpiList = new ArrayList<>();
        cpiRepository.findAll().forEach(cpi -> cpiList.add(cpi));
        return cpiList;
    }

    @Override
    public List<CPI> getAllBetween(final int startYear, final int endYear) {
        return cpiRepository.findByYearIsBetween(startYear, endYear);
    }
}
