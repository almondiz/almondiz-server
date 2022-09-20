package org.almondiz.almondiz.exception.exception;

public class PostNotPermittedException extends RuntimeException{
    public PostNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostNotPermittedException(String msg) {
        super(msg);
    }

    public PostNotPermittedException() {
        super();
    }
}
