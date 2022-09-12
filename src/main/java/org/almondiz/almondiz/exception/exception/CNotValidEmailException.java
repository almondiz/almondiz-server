package org.almondiz.almondiz.exception.exception;

public class CNotValidEmailException extends RuntimeException{

    public CNotValidEmailException(String msg, Throwable t) {
        super(msg, t);
    }

    public CNotValidEmailException(String msg) {
        super(msg);
    }

    public CNotValidEmailException() {
        super();
    }
}
