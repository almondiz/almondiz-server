package org.almondiz.almondiz.exception.exception;

public class CommentNotFoundException extends  RuntimeException {

    public CommentNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CommentNotFoundException(String msg) {
        super(msg);
    }

    public CommentNotFoundException() {
        super();
    }

}
