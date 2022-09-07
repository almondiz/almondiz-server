package org.almondiz.almondiz.exception.exception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public StoreNotFoundException(String msg) {
        super(msg);
    }

    public StoreNotFoundException() {
        super();
    }


}
