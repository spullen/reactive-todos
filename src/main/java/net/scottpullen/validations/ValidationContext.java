package net.scottpullen.validations;

import java.util.ArrayList;
import java.util.List;

public class ValidationContext {
    private final Object target;
    private final List<ValidationError> errors;

    public ValidationContext(final Object target) {
        this.target = target;
        this.errors = new ArrayList<ValidationError>();
    }


}
