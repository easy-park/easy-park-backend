package com.oocl.easyparkbackend.Manage.Exception;

import com.oocl.easyparkbackend.exception.BaseResponseException;

public class FrozenManagerException extends BaseResponseException {

    public FrozenManagerException() {
        super(700, "经理账号被冻结");
    }
}
