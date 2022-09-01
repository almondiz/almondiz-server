package org.almondiz.almondiz.exception.exception;

public class CFollowNotFoundException extends RuntimeException{
    public CFollowNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CFollowNotFoundException(String msg) {
        super(msg);
    }

    public CFollowNotFoundException() {
        super();
    }
}
