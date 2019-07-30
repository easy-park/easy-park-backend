package com.oocl.easyparkbackend.Employee.Exception;

public class ClerkIdErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Clerk id not found.";
    }
}
