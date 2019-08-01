package com.oocl.easyparkbackend.Manage.Exception;

import com.oocl.easyparkbackend.base.BaseException;

public class FrozenManagerException extends BaseException {

    public FrozenManagerException() {
        super(700, "经理账号被冻结");
    }
}
