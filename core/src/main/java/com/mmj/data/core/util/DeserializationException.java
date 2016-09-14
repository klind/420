package com.mmj.data.core.util;

public class DeserializationException extends RuntimeException {
    public DeserializationException() {
    }

    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(Throwable throwable) {
        super(throwable);
    }

    public DeserializationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
