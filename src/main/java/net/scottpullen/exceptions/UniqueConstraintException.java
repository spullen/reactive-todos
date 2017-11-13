package net.scottpullen.exceptions;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueConstraintException(String message) {
        super(message);
    }
}
