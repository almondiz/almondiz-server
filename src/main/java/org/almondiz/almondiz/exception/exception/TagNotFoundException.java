package org.almondiz.almondiz.exception.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public TagNotFoundException(String msg) {
        super(msg);
    }

    public TagNotFoundException() {
        super();
    }

}
