package com.finki.ads.model.exceptions;

public class TypeNotFoundException extends StorageException {

    public TypeNotFoundException(String message) {
        super(message);
    }

    public TypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}