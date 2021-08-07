package com.raunheim.los.exception;

public class LosException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public LosException(String message) {
        super(message);
    }

    public LosException(String message, Throwable cause) {
        super(message, cause);
    }
}
