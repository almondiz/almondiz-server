package org.almondiz.almondiz.exception.exception;

public class PostScrapNotFoundException extends RuntimeException{

    public PostScrapNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostScrapNotFoundException(String msg) {
        super(msg);
    }

    public PostScrapNotFoundException() {
        super();
    }

}
