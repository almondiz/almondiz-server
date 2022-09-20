package org.almondiz.almondiz.exception.exception;

public class NotValidEmailException extends RuntimeException{

    public NotValidEmailException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotValidEmailException(String msg) {
        super(msg);
    }

    public NotValidEmailException() {
        super();
    }
}
