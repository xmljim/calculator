package io.xmljim.retirement.calculator.entity.stocks;

import io.xmljim.retirement.calculator.entity.ServiceResponse;
import io.xmljim.retirement.calculator.entity.ServiceResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsolidatedMarketHistory implements ServiceResult {
    public ConsolidatedMarketHistory() {
        //default constructor
    }

    private int year;
    private double yearOpen;
    private double yearClose;
    private double netChange;
    private double pctChange;
    private double aggregateChange;
    private double adjustedAggregateChange;
    private double logAggregateChange;
    private double logAdjustedAggregateChange;
}
