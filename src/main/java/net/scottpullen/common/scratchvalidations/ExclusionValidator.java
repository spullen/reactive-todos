package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates that the value is not included in a given list
 *
 * Options:
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class ExclusionValidator extends BaseValidator implements Validator {

    private ExclusionValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
