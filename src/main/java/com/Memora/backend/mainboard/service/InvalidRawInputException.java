package com.Memora.backend.mainboard.service;

public class InvalidRawInputException extends RuntimeException {

    public InvalidRawInputException(String message) {
        super(message);
    }

    public InvalidRawInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
