package org.almondiz.almondiz.exception.exception;

public class FollowNotFoundException extends RuntimeException{
    public FollowNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public FollowNotFoundException(String msg) {
        super(msg);
    }

    public FollowNotFoundException() {
        super();
    }
}
