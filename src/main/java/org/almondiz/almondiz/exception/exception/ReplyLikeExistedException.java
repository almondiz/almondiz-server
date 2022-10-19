package org.almondiz.almondiz.exception.exception;

public class ReplyLikeExistedException extends RuntimeException{

    public ReplyLikeExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReplyLikeExistedException(String msg) {
        super(msg);
    }

    public ReplyLikeExistedException() {
        super();
    }
}
