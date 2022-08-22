package com.mube.web.exception;

public class CarIdMismatchException extends RuntimeException {

    public CarIdMismatchException() {
        super();
    }

    public CarIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CarIdMismatchException(final String message) {
        super(message);
    }

    public CarIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
