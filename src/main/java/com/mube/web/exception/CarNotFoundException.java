package com.mube.web.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException() {
        super();
    }

    public CarNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CarNotFoundException(final String message) {
        super(message);
    }

    public CarNotFoundException(final Throwable cause) {
        super(cause);
    }
}