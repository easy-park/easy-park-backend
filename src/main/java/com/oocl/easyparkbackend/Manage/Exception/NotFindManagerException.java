package com.oocl.easyparkbackend.Manage.Exception;

public class NotFindManagerException extends RuntimeException {

    @Override
    public String getMessage() {
        return "not find manager";
    }
}
