package com.oocl.easyparkbackend.exception;

public class BaseResponseException extends RuntimeException {
    private int status;

    public BaseResponseException(int status, String message) {
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
