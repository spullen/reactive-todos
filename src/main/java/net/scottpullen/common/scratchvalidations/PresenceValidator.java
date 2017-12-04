package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates that the value is present
 *
 * Options:
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class PresenceValidator extends BaseValidator implements Validator {

    private PresenceValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
