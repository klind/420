package com.mmj.data.core.util;

public class SerializationException extends RuntimeException {
    public SerializationException() {
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Throwable throwable) {
        super(throwable);
    }

    public SerializationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
