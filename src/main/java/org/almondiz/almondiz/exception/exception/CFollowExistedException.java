package org.almondiz.almondiz.exception.exception;

public class CFollowExistedException extends RuntimeException {
    public CFollowExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CFollowExistedException(String msg) {
        super(msg);
    }

    public CFollowExistedException() {
        super();
    }
}
