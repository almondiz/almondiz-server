package org.almondiz.almondiz.exception.exception;

public class PostScrapExistedException extends RuntimeException {

    public PostScrapExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostScrapExistedException(String msg) {
        super(msg);
    }

    public PostScrapExistedException() {
        super();
    }

}
