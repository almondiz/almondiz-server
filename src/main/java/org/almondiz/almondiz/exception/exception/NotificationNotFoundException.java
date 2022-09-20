package org.almondiz.almondiz.exception.exception;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotificationNotFoundException(String msg) {
        super(msg);
    }

    public NotificationNotFoundException() {
        super();
    }
}
