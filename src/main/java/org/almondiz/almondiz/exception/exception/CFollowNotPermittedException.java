package org.almondiz.almondiz.exception.exception;

public class CFollowNotPermittedException extends RuntimeException{
    public CFollowNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CFollowNotPermittedException(String msg) {
        super(msg);
    }

    public CFollowNotPermittedException() {
        super();
    }
}
