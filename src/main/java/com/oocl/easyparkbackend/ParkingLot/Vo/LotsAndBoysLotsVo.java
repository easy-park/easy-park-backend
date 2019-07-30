package com.oocl.easyparkbackend.ParkingLot.Vo;

import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;

import java.util.List;

public class LotsAndBoysLotsVo {
    private List<ParkingLot> parkingLots;
    private List<ParkingLot> boysParkingLots;

    public LotsAndBoysLotsVo() {
    }

    public LotsAndBoysLotsVo(List<ParkingLot> parkingLots, List<ParkingLot> boysParkingLots) {
        this.parkingLots = parkingLots;
        this.boysParkingLots = boysParkingLots;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public List<ParkingLot> getBoysParkingLots() {
        return boysParkingLots;
    }

    public void setBoysParkingLots(List<ParkingLot> boysParkingLots) {
        this.boysParkingLots = boysParkingLots;
    }
}
