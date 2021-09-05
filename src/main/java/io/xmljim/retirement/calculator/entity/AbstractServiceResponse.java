package io.xmljim.retirement.calculator.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractServiceResponse implements ServiceResponse {
    private final List<ServiceResult> results;
    private final int count;

    public AbstractServiceResponse(List<ServiceResult> results) {
        this.results = results;
        this.count = results.size();
    }

    public AbstractServiceResponse(ServiceResult result) {
        ArrayList<ServiceResult> results = new ArrayList<>();
        if (result != null) {
            results.add(result);
        }

        this.results = results;
        this.count = results.size();
    }

    @Override
    public int getResultCount() {
        return count;
    }

    @Override
    public List<ServiceResult> getResults() {
        return results;
    }

    public String toString() {
        return "(count = " + count + ", results = " + getResults().toString() + ")";
    }
}
