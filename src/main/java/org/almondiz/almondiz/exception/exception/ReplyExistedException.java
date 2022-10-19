package org.almondiz.almondiz.exception.exception;

public class ReplyExistedException extends RuntimeException{

    public ReplyExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReplyExistedException(String msg) {
        super(msg);
    }

    public ReplyExistedException() {
        super();
    }
}
