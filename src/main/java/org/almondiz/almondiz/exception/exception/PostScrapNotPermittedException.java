package org.almondiz.almondiz.exception.exception;

public class PostScrapNotPermittedException extends RuntimeException{
    public PostScrapNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostScrapNotPermittedException(String msg) {
        super(msg);
    }

    public PostScrapNotPermittedException() {
        super();
    }
}
