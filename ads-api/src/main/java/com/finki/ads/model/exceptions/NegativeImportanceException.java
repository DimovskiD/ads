package com.finki.ads.model.exceptions;

public class NegativeImportanceException extends RuntimeException {

    public NegativeImportanceException(String message) {
        super(message);
    }

    public NegativeImportanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
