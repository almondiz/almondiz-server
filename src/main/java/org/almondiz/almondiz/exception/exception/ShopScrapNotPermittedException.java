package org.almondiz.almondiz.exception.exception;

public class ShopScrapNotPermittedException extends RuntimeException{
    public ShopScrapNotPermittedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ShopScrapNotPermittedException(String msg) {
        super(msg);
    }

    public ShopScrapNotPermittedException() { super(); }
}
