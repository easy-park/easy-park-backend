package com.oocl.easyparkbackend.Manage.Exception;

import com.oocl.easyparkbackend.exception.BaseResponseException;

public class NotFindManagerException extends BaseResponseException {
    public NotFindManagerException() {
        super(400, "Account not exists");
    }
}
