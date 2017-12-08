package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates that the value is included in a given list
 *
 * Options:
 * * in: list
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class InclusionValidator extends BaseValidator implements Validator {

    private InclusionValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
