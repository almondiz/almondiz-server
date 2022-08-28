package org.almondiz.almondiz.exception.exception;

public class CNotificationNotPermittedException extends RuntimeException{
    public CNotificationNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CNotificationNotPermittedException(String msg) {
        super(msg);
    }

    public CNotificationNotPermittedException() {
        super();
    }
}
