package io.xmljim.retirement.calculator.entity;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;
import java.util.List;

public interface ServiceResponse extends Serializable {

    @JsonGetter("count")
    int getResultCount();

    @JsonGetter("results")
    List<ServiceResult> getResults();
}
