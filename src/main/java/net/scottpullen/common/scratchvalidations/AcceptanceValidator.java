package net.scottpullen.common.scratchvalidations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Validates whether a value has been accepted
 *
 * Defaults to check if the value is true
 *
 * Options:
 * * accept: "value" (ex. accept: "yes")
 * or
 * * accept: ["yes", "YES", "accept", "true"]
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class AcceptanceValidator extends BaseValidator implements Validator {
    private static final List<String> VALID_STRINGS = Collections.unmodifiableList(Arrays.asList("yes", "accept", "true"));

    private AcceptanceValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        if(this.attribute instanceof Boolean) {
            return (Boolean) this.attribute;
        } else if(this.attribute instanceof String) {
            return VALID_STRINGS.contains(((String) this.attribute).toLowerCase());
        } else {
            return false;
        }
    }
}
