package io.xmljim.retirement.calculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RetirementInput implements Serializable {
    private int age;
    private int retirementAge;
    private double currentSalary;
    private double colaPct;
    private double currentRetirementBalance;
    private double selfContributionPct;
    private double employerContributionPct;
    private double investmentStyle;
    private String contributionFrequency;
    private double postRetirementInterestRate;
    private String distributionFrequency;
    private int retirementDuration;
    private double incomeReplacementPct;
    private double annualizedDistribution;
}
