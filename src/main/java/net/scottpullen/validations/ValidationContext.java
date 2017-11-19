package net.scottpullen.validations;

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

    public ValidationContext acceptance() {
        return validate(new AcceptanceValidator());
    }

    public ValidationContext confirmation() {
        return validate(new ConfirmationValidator());
    }

    public ValidationContext format() {
        return validate(new FormatValidator());
    }

    public ValidationContext length() {
        return validate(new LengthValidator());
    }

    public ValidationContext numericality() {
        return validate(new NumericalityValidator());
    }

    public ValidationContext presence() {
        return validate(new PresenceValidator());
    }

    public ValidationContext uniqueness() {
        return validate(new UniquenessValidator());
    }

    public ValidationContext validate(Validator validator) {
        validators.add(validator);
        return this;
    }
}
