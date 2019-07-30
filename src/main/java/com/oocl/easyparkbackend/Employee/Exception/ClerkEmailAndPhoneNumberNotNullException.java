package com.oocl.easyparkbackend.Employee.Exception;

public class ClerkEmailAndPhoneNumberNotNullException extends RuntimeException {

    @Override
    public String getMessage() {
        return "clerk's email and phone number can't be null.";
    }
}
