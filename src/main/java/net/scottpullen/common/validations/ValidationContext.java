package net.scottpullen.common.validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationContext<T> {
    private final T target;
    private final List<Validator> validators;
    private final Map<String, List<ValidationError>> errors;

    public ValidationContext(final T target) {
        this.target = target;
        this.validators = new ArrayList<Validator>();
        this.errors = new HashMap<String, List<ValidationError>>();
    }

    public void addError(String field, ValidationError validationError) {
        if(errors.containsKey(field)) {
            errors.put(field, new ArrayList<ValidationError>());
        }

        errors.get(field).add(validationError);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public T getTarget() {
        return target;
    }

    // value
    public ValidationContext acceptance() {
        return validate(new AcceptanceValidator());
    }

    // value, otherValue
    public ValidationContext confirmation() {
        return validate(new ConfirmationValidator());
    }

    // value, list/array
    public ValidationContext exclusion() {
        return validate(new ExclusionValidator());
    }

    // value, expression
    public ValidationContext format() {
        return validate(new FormatValidator());
    }

    // value, list/array
    public ValidationContext inclusion() {
        return validate(new InclusionValidator());
    }

    // value, options
    public ValidationContext length() {
        return validate(new LengthValidator());
    }

    // value, options
    public ValidationContext numericality() {
        return validate(new NumericalityValidator());
    }

    // value, options
    public ValidationContext presence(String field, Object value) {
        return validate(new PresenceValidator());
    }

    // value, function
    public ValidationContext uniqueness() {
        return validate(new UniquenessValidator());
    }

    public ValidationContext validate(Validator validator) {
        validators.add(validator);
        return this;
    }
}
