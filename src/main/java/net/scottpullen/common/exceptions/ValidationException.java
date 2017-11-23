package net.scottpullen.common.exceptions;

import com.baidu.unbiz.fluentvalidator.ComplexResult;

public class ValidationException extends RuntimeException {

    private ComplexResult validationResult;

    public ValidationException(ComplexResult validationResult) {
        super();
        this.validationResult = validationResult;
    }

    public ComplexResult getValidationResult() {
        return this.validationResult;
    }
}
