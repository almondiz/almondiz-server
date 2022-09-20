package org.almondiz.almondiz.exception.exception;

public class FollowExistedException extends RuntimeException {
    public FollowExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public FollowExistedException(String msg) {
        super(msg);
    }

    public FollowExistedException() {
        super();
    }
}
