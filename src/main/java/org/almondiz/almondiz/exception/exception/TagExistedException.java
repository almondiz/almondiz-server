package org.almondiz.almondiz.exception.exception;

public class TagExistedException extends RuntimeException {

    public TagExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public TagExistedException(String msg) {
        super(msg);
    }

    public TagExistedException() {
        super();
    }
}
