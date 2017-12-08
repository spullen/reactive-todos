package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates that the value matches some format
 *
 * Options:
 * * with: (ex. some regular expression)
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class FormatValidator extends BaseValidator implements Validator {

    private FormatValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
