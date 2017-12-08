package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates the confirmation of values
 *
 * Options:
 * * caseSensitive (defaults to true, ex. caseSensitive: false)
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class ConfirmationValidator extends BaseValidator implements Validator {

    private ConfirmationValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
