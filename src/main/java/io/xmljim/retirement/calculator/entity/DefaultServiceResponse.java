package io.xmljim.retirement.calculator.entity;

import java.util.List;

public class DefaultServiceResponse extends AbstractServiceResponse {
    public DefaultServiceResponse(final List<ServiceResult> results) {
        super(results);
    }

    public DefaultServiceResponse(ServiceResult result) {
        super(result);
    }
}
