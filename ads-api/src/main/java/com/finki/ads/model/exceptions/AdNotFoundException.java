package com.finki.ads.model.exceptions;

public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException(String message) {
        super(message);
    }

    public AdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
