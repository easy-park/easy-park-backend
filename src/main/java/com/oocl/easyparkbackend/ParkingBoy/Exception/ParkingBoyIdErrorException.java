package com.oocl.easyparkbackend.ParkingBoy.Exception;

public class ParkingBoyIdErrorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "parkingBoy Id error";
    }
}
