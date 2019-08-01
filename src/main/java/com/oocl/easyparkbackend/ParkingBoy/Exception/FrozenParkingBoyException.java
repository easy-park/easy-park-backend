package com.oocl.easyparkbackend.ParkingBoy.Exception;

import com.oocl.easyparkbackend.base.BaseException;

public class FrozenParkingBoyException extends BaseException {

    public FrozenParkingBoyException() {
        super(800, "该账号已被冻结");
    }
}
