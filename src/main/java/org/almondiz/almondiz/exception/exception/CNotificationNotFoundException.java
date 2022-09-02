package org.almondiz.almondiz.exception.exception;

public class CNotificationNotFoundException extends RuntimeException {

    public CNotificationNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CNotificationNotFoundException(String msg) {
        super(msg);
    }

    public CNotificationNotFoundException() {
        super();
    }
}
