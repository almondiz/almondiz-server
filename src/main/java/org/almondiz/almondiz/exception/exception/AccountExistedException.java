package org.almondiz.almondiz.exception.exception;

public class AccountExistedException extends RuntimeException {

    public AccountExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AccountExistedException(String msg) {
        super(msg);
    }

    public AccountExistedException() {
        super();
    }

}
