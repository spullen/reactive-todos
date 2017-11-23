package net.scottpullen.common.scratchvalidations;

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
public class AcceptanceValidator extends Validator {
    public void validate() {

    }
}
