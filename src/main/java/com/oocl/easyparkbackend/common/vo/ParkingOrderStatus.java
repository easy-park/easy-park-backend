package com.oocl.easyparkbackend.common.vo;

interface ParkingOrderStatus {
    int PLACE_Order = 1;
    int RECEIVED_ORDER = 2;
    int PARKED_CAR = 3;
    int WAIT_FETCH_CAR = 4;
    int FETCHING_CAR = 5;
    int FINNISHED = 6;
}