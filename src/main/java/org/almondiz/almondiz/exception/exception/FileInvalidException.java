package org.almondiz.almondiz.exception.exception;

public class FileInvalidException extends RuntimeException{
    public FileInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public FileInvalidException(String msg) {
        super(msg);
    }

    public FileInvalidException() {
        super();
    }
}
