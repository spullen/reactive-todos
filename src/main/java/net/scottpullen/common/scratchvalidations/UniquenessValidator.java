package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates the uniqueness of a value
 *
 * Options:
 * * test: (A function that is used to test the uniqueness)
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class UniquenessValidator extends BaseValidator implements Validator {

    private UniquenessValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
