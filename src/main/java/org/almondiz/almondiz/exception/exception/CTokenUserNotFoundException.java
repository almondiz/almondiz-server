package org.almondiz.almondiz.exception.exception;

public class CTokenUserNotFoundException extends RuntimeException{

    public CTokenUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CTokenUserNotFoundException(String msg) {
        super(msg);
    }

    public CTokenUserNotFoundException() {
        super();
    }
}
