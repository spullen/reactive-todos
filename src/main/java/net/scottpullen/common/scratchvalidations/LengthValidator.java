package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates the length of an attribute
 *
 * Option:
 * * minimum: (Optional, ex minimum: 6)
 * * maximum: (Optional, ex maximum: 10)
 * * exactly: (Optional, ex exactly: 8)
 * * message: (Optional ex. message: "alternative.message.key")
 *
 * minimum and maximum can be used together to provide a range
 *
 */
public class LengthValidator extends BaseValidator implements Validator {

    private LengthValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
