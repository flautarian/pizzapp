package com.pizzapp.base.exception;

public class StatusNotAllowedException extends RuntimeException {
    public StatusNotAllowedException(String message) {
        super(message);
    }
}