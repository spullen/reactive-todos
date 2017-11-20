package net.scottpullen.common.validations;

public class ValidationError {
    private final String messageKey;

    public ValidationError(final String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return this.messageKey;
    }
}
