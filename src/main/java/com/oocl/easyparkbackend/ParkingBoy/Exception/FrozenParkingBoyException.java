package com.oocl.easyparkbackend.ParkingBoy.Exception;

import com.oocl.easyparkbackend.exception.BaseResponseException;

public class FrozenParkingBoyException extends BaseResponseException {

    public FrozenParkingBoyException() {
        super(800, "该账号已被冻结");
    }
}
