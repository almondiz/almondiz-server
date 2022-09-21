package org.almondiz.almondiz.exception.exception;

public class RefreshTokenException extends RuntimeException{
    public RefreshTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public RefreshTokenException(String msg) {
        super(msg);
    }

    public RefreshTokenException() {
        super();
    }
}
