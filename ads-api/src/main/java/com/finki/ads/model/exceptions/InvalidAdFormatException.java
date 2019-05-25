package com.finki.ads.model.exceptions;

public class InvalidAdFormatException extends RuntimeException {

    public InvalidAdFormatException(String message) {
        super(message);
    }

    public InvalidAdFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
