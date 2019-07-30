package com.oocl.easyparkbackend.Clerk.Exception;

public class ClerkIdErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Clerk id not found.";
    }
}
