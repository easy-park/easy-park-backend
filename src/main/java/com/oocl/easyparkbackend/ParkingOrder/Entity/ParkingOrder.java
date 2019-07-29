package com.oocl.easyparkbackend.ParkingOrder.Entity;

import com.oocl.easyparkbackend.Customer.Entity.Customer;
import com.oocl.easyparkbackend.ParkingBoy.Entity.ParkingBoy;
import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "parking_order")
public class ParkingOrder {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(name = "id")
    private String Id;

    @Column(name = "car_number")
    @NotNull
    private String carNumber;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    @NotNull
    private Integer status;

    @OneToOne
    private ParkingBoy parkingBoy;

    @OneToOne
    private ParkingLot parkingLot;

    @ManyToOne
    private Customer customer;

    public ParkingOrder() {
    }

    public ParkingOrder(String id, @NotNull String carNumber, Timestamp startTime, Timestamp endTime, Double price, @NotNull Integer status, ParkingBoy parkingBoy, ParkingLot parkingLot) {
        Id = id;
        this.carNumber = carNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
        this.parkingBoy = parkingBoy;
        this.parkingLot = parkingLot;
    }

    public ParkingOrder(String id, @NotNull String carNumber, Timestamp startTime, Timestamp endTime, Double price, @NotNull Integer status, ParkingBoy parkingBoy, ParkingLot parkingLot, Customer customer) {
        Id = id;
        this.carNumber = carNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.status = status;
        this.parkingBoy = parkingBoy;
        this.parkingLot = parkingLot;
        this.customer = customer;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ParkingBoy getParkingBoy() {
        return parkingBoy;
    }

    public void setParkingBoy(ParkingBoy parkingBoy) {
        this.parkingBoy = parkingBoy;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
