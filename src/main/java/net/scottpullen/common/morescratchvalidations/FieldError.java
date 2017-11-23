package net.scottpullen.common.morescratchvalidations;

public class FieldError {
    private String field;
    private Object rejectedValue;
    private String message;

    public FieldError(String field, Object rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
