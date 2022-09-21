package org.almondiz.almondiz.exception.exception;

public class TokenUserNotFoundException extends RuntimeException{

    public TokenUserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenUserNotFoundException(String msg) {
        super(msg);
    }

    public TokenUserNotFoundException() {
        super();
    }
}
