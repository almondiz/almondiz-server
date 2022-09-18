package org.almondiz.almondiz.exception.exception;

public class StoreExistedException extends RuntimeException{
    public StoreExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public StoreExistedException(String msg) {
        super(msg);
    }

    public StoreExistedException() {
        super();
    }
}
