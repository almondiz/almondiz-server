package org.almondiz.almondiz.exception.exception;

public class ExpiredTokenException extends RuntimeException{

    public ExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public ExpiredTokenException(String msg) {
        super(msg);
    }

    public ExpiredTokenException() {
        super();
    }
}
