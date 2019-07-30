package com.oocl.easyparkbackend.ParkingLot.Exception;

public class ParkingLotRangeErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "The range should't be zero or null.";
    }
}
