package org.almondiz.almondiz.exception.exception;

public class CExpiredTokenException extends RuntimeException{

    public CExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public CExpiredTokenException(String msg) {
        super(msg);
    }

    public CExpiredTokenException() {
        super();
    }
}
