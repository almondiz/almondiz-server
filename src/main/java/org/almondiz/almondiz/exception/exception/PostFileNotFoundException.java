package org.almondiz.almondiz.exception.exception;

public class PostFileNotFoundException extends RuntimeException {

    public PostFileNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostFileNotFoundException(String msg) {
        super(msg);
    }

    public PostFileNotFoundException() {
        super();
    }

}
