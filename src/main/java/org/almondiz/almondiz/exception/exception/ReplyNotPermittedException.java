package org.almondiz.almondiz.exception.exception;

public class ReplyNotPermittedException extends RuntimeException{

    public ReplyNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReplyNotPermittedException(String msg) {
        super(msg);
    }

    public ReplyNotPermittedException() {
        super();
    }
}
