package net.scottpullen.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
