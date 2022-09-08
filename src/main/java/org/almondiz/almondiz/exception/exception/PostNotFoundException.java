package org.almondiz.almondiz.exception.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostNotFoundException(String msg) {
        super(msg);
    }

    public PostNotFoundException() {
        super();
    }

}
