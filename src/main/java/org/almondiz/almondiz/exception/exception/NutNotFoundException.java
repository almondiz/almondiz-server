package org.almondiz.almondiz.exception.exception;

public class NutNotFoundException extends RuntimeException {

    public NutNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public NutNotFoundException(String msg) {
        super(msg);
    }

    public NutNotFoundException() {
        super();
    }

}
