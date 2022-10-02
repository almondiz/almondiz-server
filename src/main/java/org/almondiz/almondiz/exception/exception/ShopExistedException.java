package org.almondiz.almondiz.exception.exception;

public class ShopExistedException extends RuntimeException{
    public ShopExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ShopExistedException(String msg) {
        super(msg);
    }

    public ShopExistedException() {
        super();
    }
}
