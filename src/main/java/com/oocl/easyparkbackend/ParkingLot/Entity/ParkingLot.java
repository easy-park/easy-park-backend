package com.oocl.easyparkbackend.ParkingLot.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking_lot")
public class ParkingLot {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(name = "id")
    private String Id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "capacity")
    @NotNull
    private Integer capacity;

    @Column(name = "available")
    @NotNull
    private Integer available;

    @Column(name = "status")
    private Boolean status;

    public ParkingLot() {
    }

    public ParkingLot(String id, @NotNull String name, @NotNull Integer capacity, @NotNull Integer available) {
        Id = id;
        this.name = name;
        this.capacity = capacity;
        this.available = available;
    }

    public ParkingLot(String id, @NotNull String name, @NotNull Integer capacity, @NotNull Integer available, Boolean status) {
        Id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
