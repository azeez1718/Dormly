package com.example.Dormly.exceptions;

public class ThreadNotFoundException extends RuntimeException {
    public ThreadNotFoundException(String message) {
        super(message);
    }
}
