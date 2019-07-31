package com.oocl.easyparkbackend.authentication;

import com.oocl.easyparkbackend.base.BaseException;

public class AuthenticateException extends BaseException {
    public AuthenticateException() {
        super(400, "Authenticate failed");
    }
}
