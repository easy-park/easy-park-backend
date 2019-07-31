package com.oocl.easyparkbackend.base;

public class BaseException extends RuntimeException {
    private int status;

    public BaseException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
