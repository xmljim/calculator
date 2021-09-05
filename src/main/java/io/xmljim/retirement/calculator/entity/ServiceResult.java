package io.xmljim.retirement.calculator.entity;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

public interface ServiceResult extends Serializable {
    @JsonGetter("@type")
    default String getType() {
        return this.getClass().getSimpleName();
    }
}
