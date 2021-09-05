package io.xmljim.retirement.calculator.controllers;

import io.xmljim.algorithms.functions.financial.RetirementModel;
import io.xmljim.retirement.calculator.dto.RetirementInput;
import io.xmljim.retirement.calculator.entity.algorithms.ModelResult;
import io.xmljim.retirement.calculator.entity.algorithms.ModelResultImpl;
import io.xmljim.retirement.service.RetirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retirement")
public class RetirementController {

    @Autowired
    RetirementService retirementService;

    @GetMapping("/")
    String sayHello() {
        return "Retirement";
    }

    @CrossOrigin(origins = {"http://localhost:3000"})
    @PostMapping("/calculate")
    ModelResult getRetirementModel(@RequestBody  RetirementInput retirementInput) {
        RetirementModel model = retirementService.getRetirementModel(retirementInput);
        model.solve();
        return new ModelResultImpl(model);
    }
}
