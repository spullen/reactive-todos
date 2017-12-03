package net.scottpullen.common.scratchvalidations;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ValidationContext<T> {
    private final T target;
    private final String targetName;
    private final List<ValidationChain> validationChains;
    private boolean failFast = false;
    private boolean throwExceptionOnValidationFailure = false;

    public ValidationContext(final T target, final String targetName) {
        this.target = target;
        this.targetName = targetName;
        this.validationChains = new ArrayList<>();
    }

    public ValidationContext on(String attributeName, Object attribute, Consumer<ValidationChain> validations) {
        ValidationChain chain = new ValidationChain(this, attributeName, attribute);
        validationChains.add(chain);
        validations.accept(chain);
        return this;
    }

    public ValidationContext failFast() {
        failFast = true;
        return this;
    }

    public ValidationContext throwError() {
        throwExceptionOnValidationFailure = true;
        return this;
    }

    public ValidationResult execute() {
        // TODO run through validation chains and their validators
        return new ValidationResult();
    }
}
