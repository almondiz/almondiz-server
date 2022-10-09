package org.almondiz.almondiz.exception.exception;

public class TagPostNotFoundException extends RuntimeException{

    public TagPostNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public TagPostNotFoundException(String msg) {
        super(msg);
    }

    public TagPostNotFoundException() {
        super();
    }

}
