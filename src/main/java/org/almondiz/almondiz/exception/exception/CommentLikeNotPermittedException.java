package org.almondiz.almondiz.exception.exception;

public class CommentLikeNotPermittedException extends RuntimeException{

    public CommentLikeNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CommentLikeNotPermittedException(String msg) {
        super(msg);
    }

    public CommentLikeNotPermittedException() {
        super();
    }
}
