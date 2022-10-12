package org.almondiz.almondiz.exception.exception;

public class ReportExistedException extends RuntimeException{

    public ReportExistedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReportExistedException(String msg) {
        super(msg);
    }

    public ReportExistedException() {
        super();
    }

}
