package com.oocl.easyparkbackend.ParkingLot.Exception;

public class ParkingLotNameAndCapacityNotNull extends RuntimeException {
    @Override
    public String getMessage() {
        return "parking lot name and capacity can't be null.";
    }
}
