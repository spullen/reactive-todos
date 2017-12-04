package net.scottpullen.common.scratchvalidations;

import java.util.ArrayList;
import java.util.List;

public class ValidationChain {
    private ValidationContext context;
    private String attributeName;
    private Object attribute;
    private List<Validator> validators;

    public ValidationChain(ValidationContext context, String attributeName, Object attribute) {
        this.context = context;
        this.attributeName = attributeName;
        this.attribute = attribute;
        this.validators = new ArrayList<>();
    }

    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    public List<Validator> getValidators() {
        return validators;
    }
}
