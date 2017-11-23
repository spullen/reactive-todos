package net.scottpullen.common.morescratchvalidations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Errors {
    private Map<String, List<FieldError>> errors;

    public Errors() {
        errors = new HashMap<>();
    }

    public void addError(String field, Object rejectedValue, String message) {
        errors.getOrDefault(field, new ArrayList<>()).add(new FieldError(field, rejectedValue, message));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
