package org.almondiz.almondiz.exception.exception;

public class CommentLikeNotFoundException extends RuntimeException{

    public CommentLikeNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CommentLikeNotFoundException(String msg) {
        super(msg);
    }

    public CommentLikeNotFoundException() {
        super();
    }
}
