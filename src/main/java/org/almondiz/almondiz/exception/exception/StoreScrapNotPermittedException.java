package org.almondiz.almondiz.exception.exception;

public class StoreScrapNotPermittedException extends RuntimeException{
    public StoreScrapNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public StoreScrapNotPermittedException(String msg) {
        super(msg);
    }

    public StoreScrapNotPermittedException() { super(); }
}
