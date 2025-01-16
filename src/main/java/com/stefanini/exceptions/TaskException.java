package com.stefanini.exceptions;

public class TaskException extends RuntimeException {
    public TaskException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public TaskException(String message) {
        super(message);
    }
}
