package org.almondiz.almondiz.exception.exception;

public class ReplyNotFoundException extends RuntimeException{

    public ReplyNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReplyNotFoundException(String msg) {
        super(msg);
    }

    public ReplyNotFoundException() {
        super();
    }
}
