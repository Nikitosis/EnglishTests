package io.english.exceptions;

public class InvalidAccessException extends RuntimeException{
    public InvalidAccessException() {
        super();
    }

    public InvalidAccessException(String message) {
        super(message);
    }

    public InvalidAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAccessException(Throwable cause) {
        super(cause);
    }
}
