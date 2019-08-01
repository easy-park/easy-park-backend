package com.oocl.easyparkbackend.exception.authentication;

import com.oocl.easyparkbackend.exception.BaseResponseException;

public class AuthenticateException extends BaseResponseException {
    public AuthenticateException() {
        super(400, "Authenticate failed");
    }
}
