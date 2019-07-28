package com.oocl.easyparkbackend.ParkingLot.Exception;

public class ParkingLotIdErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "ParkingLotId Error";
    }
}
