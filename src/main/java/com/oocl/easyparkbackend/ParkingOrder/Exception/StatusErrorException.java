package com.oocl.easyparkbackend.ParkingOrder.Exception;

public class StatusErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Status Error";
    }
}
