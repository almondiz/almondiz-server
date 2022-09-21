package org.almondiz.almondiz.exception.exception;

public class FollowNotPermittedException extends RuntimeException{
    public FollowNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public FollowNotPermittedException(String msg) {
        super(msg);
    }

    public FollowNotPermittedException() {
        super();
    }
}
