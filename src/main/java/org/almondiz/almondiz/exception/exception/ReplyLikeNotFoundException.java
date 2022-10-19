package org.almondiz.almondiz.exception.exception;

public class ReplyLikeNotFoundException extends RuntimeException{

    public ReplyLikeNotFoundException (String msg, Throwable t) {
        super(msg, t);
    }

    public ReplyLikeNotFoundException (String msg) {
        super(msg);
    }

    public ReplyLikeNotFoundException () {
        super();
    }
}
