package org.almondiz.almondiz.exception.exception;

public class FollowMySelfException extends RuntimeException {
    public FollowMySelfException(String msg, Throwable t) {
        super(msg, t);
    }

    public FollowMySelfException(String msg) {
        super(msg);
    }

    public FollowMySelfException() {
        super();
    }
}
