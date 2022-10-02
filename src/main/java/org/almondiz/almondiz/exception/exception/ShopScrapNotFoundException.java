package org.almondiz.almondiz.exception.exception;

public class ShopScrapNotFoundException extends RuntimeException {

    public ShopScrapNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ShopScrapNotFoundException(String msg) {
        super(msg);
    }

    public ShopScrapNotFoundException() { super(); }
}
