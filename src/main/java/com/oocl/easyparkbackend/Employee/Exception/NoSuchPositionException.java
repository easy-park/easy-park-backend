package com.oocl.easyparkbackend.Employee.Exception;

public class NoSuchPositionException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No such positions in clerks.";
    }
}
