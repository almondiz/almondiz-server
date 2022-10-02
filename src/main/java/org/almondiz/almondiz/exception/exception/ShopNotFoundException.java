package org.almondiz.almondiz.exception.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ShopNotFoundException(String msg) {
        super(msg);
    }

    public ShopNotFoundException() {
        super();
    }


}
