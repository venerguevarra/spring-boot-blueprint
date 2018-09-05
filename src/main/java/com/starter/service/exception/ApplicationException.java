package com.starter.service.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException(Throwable t) {
        super(t);
    }

    public ApplicationException(String message, Throwable t) {
        super(message, t);
    }

    public ApplicationException(String message) {
        super(message);
    }
}
