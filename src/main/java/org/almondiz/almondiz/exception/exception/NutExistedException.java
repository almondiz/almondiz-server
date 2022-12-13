package org.almondiz.almondiz.exception.exception;

public class NutExistedException extends RuntimeException {

    public NutExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public NutExistedException(String msg) {
        super(msg);
    }

    public NutExistedException() {
        super();
    }

}
