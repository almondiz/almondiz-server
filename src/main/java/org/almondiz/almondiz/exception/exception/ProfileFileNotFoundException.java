package org.almondiz.almondiz.exception.exception;

public class ProfileFileNotFoundException extends RuntimeException {

    public ProfileFileNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ProfileFileNotFoundException(String msg) {
        super(msg);
    }

    public ProfileFileNotFoundException() {
        super();
    }

}
