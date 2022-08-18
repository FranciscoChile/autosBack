package com.pancho.repository;

public class StorageException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 164476491801626007L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}