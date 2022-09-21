package org.almondiz.almondiz.exception.exception;

public class CommentNotPermittedException extends RuntimeException{
    public CommentNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CommentNotPermittedException(String msg) {
        super(msg);
    }

    public CommentNotPermittedException() {
        super();
    }
}
