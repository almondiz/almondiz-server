package org.almondiz.almondiz.exception.exception;

public class NotificationNotPermittedException extends RuntimeException{
    public NotificationNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotificationNotPermittedException(String msg) {
        super(msg);
    }

    public NotificationNotPermittedException() {
        super();
    }
}
