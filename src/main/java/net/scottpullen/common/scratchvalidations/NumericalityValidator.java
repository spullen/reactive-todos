package net.scottpullen.common.scratchvalidations;

import java.util.Optional;

/**
 * Validates the values numericality characteristics
 *
 * Options:
 * * gt: (Optional, ex. gt: 4, greater than)
 * * gte: (Optional, ex. gte: 6, greater than or equal to)
 * * lt: (Optional, ex. lt: 10, less than)
 * * lte: (Optional, ex. lte: 34, less than or equal to)
 * * odd: (Optional, ex. odd: true)
 * * even: (Optional, ex. even: true)
 * * message: (Optional ex. message: "alternative.message.key")
 *
 */
public class NumericalityValidator extends BaseValidator implements Validator {

    private NumericalityValidator(String attributeName, Object attribute, Optional<String> messageKey) {
        super(attributeName, attribute, messageKey);
    }

    public boolean validate() {
        return false;
    }
}
