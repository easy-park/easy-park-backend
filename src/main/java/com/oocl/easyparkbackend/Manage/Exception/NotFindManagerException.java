package com.oocl.easyparkbackend.Manage.Exception;

import com.oocl.easyparkbackend.base.BaseException;

public class NotFindManagerException extends BaseException {
    public NotFindManagerException() {
        super(400, "Account not exists");
    }
}
