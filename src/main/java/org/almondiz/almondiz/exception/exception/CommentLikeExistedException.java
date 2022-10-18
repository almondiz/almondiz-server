package org.almondiz.almondiz.exception.exception;

public class CommentLikeExistedException extends RuntimeException{
    public CommentLikeExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CommentLikeExistedException(String msg) {
        super(msg);
    }

    public CommentLikeExistedException() {
        super();
    }
}
