package org.almondiz.almondiz.exception.exception;

public class ShopScrapExistedException extends RuntimeException {

    public ShopScrapExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ShopScrapExistedException(String msg) { super(msg); }

    public ShopScrapExistedException() { super(); }
}
