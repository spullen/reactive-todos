package net.scottpullen.validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationContext<T> {
    private final T target;
    private final Map<String, List<ValidationError>> errors;

    public ValidationContext(final T target) {
        this.target = target;
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
}
