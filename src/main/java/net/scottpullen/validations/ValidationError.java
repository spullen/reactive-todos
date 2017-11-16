package net.scottpullen.validations;

public class ValidationError {
    private final String field;
    private final String messageKey;

    public ValidationError(final String field, final String messageKey) {
        this.field = field;
        this.messageKey = messageKey;
    }

    public String getField() {
        return this.field;
    }

    public String getMessageKey() {
        return this.messageKey;
    }
}
