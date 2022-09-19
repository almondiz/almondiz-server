package org.almondiz.almondiz.exception.exception;

public class StoreScrapNotFoundException extends RuntimeException {

    public StoreScrapNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public StoreScrapNotFoundException(String msg) {
        super(msg);
    }

    public StoreScrapNotFoundException() { super(); }
}
