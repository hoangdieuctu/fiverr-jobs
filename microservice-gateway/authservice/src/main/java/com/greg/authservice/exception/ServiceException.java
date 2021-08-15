package com.greg.authservice.exception;

public class ServiceException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }
}
