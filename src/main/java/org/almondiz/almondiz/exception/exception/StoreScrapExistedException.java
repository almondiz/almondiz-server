package org.almondiz.almondiz.exception.exception;

public class StoreScrapExistedException extends RuntimeException {

    public StoreScrapExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public StoreScrapExistedException(String msg) { super(msg); }

    public StoreScrapExistedException() { super(); }
}
