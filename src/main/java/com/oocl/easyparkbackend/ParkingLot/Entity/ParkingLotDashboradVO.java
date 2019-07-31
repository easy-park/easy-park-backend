package com.oocl.easyparkbackend.ParkingLot.Entity;

import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;

public class ParkingLotDashboradVO {
    public static final int STATUS_FROZEN = 0;
    public static final int STATUS_ACTIVE = 1;

    private String Id;
    private String name;
    private Integer capacity;
    private Integer available;
    private Integer status;
    private ParkingBoy parkingBoy;

    public ParkingLotDashboradVO() {
    }

    public ParkingLotDashboradVO(String id, String name, Integer capacity, Integer available) {
        Id = id;
        this.name = name;
        this.capacity = capacity;
        this.available = available;
    }

    public ParkingLotDashboradVO(String id, String name, Integer capacity, Integer available, Integer status) {
        Id = id;
        this.name = name;
        this.capacity = capacity;
        this.available = available;
        this.status = status;
    }

    public ParkingLotDashboradVO(ParkingLot parkingLot,ParkingBoy parkingBoy){
        Id = parkingLot.getId();
        this.name = parkingLot.getName();
        this.capacity = parkingLot.getCapacity();
        this.available = parkingLot.getAvailable();
        this.status = parkingLot.getStatus();
        this.parkingBoy = parkingBoy;
    }

    public ParkingLotDashboradVO( String name, Integer capacity, Integer available) {
        this.name = name;
        this.capacity = capacity;
        this.available = available;
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ParkingBoy getParkingBoy() {
        return parkingBoy;
    }

    public void setParkingBoy(ParkingBoy parkingBoy) {
        this.parkingBoy = parkingBoy;
    }

}
