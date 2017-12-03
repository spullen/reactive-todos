package net.scottpullen.common.scratchvalidations;

public class ValidationError {
    private String attributeName;
    private Object rejectedValue;
    private String message;

    public ValidationError(String attributeName, Object rejectedValue, String message) {
        this.attributeName = attributeName;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}
