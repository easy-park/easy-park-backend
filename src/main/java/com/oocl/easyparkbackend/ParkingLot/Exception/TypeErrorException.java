package com.oocl.easyparkbackend.ParkingLot.Exception;

public class TypeErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "type error ";
    }
}
