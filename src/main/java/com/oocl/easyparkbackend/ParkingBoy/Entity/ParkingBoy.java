package com.oocl.easyparkbackend.ParkingBoy.Entity;

import com.oocl.easyparkbackend.ParkingLot.Entity.ParkingLot;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "parking_boy")
public class ParkingBoy {
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @Column(name = "id")
    private String Id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;

    @Column(name = "status")
    @NotNull
    private Integer status;

    @Column(name = "email")
    @NotNull
    private String email;

    @OneToMany(fetch=FetchType.EAGER)
    private List<ParkingLot> parkingLotList;

    public ParkingBoy() {
    }

    public ParkingBoy(String id, @NotNull String username, @NotNull String password, @NotNull String name, @NotNull String phoneNumber, @NotNull Integer status, @NotNull String email, List<ParkingLot> parkingLotList) {
        Id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.email = email;
        this.parkingLotList = parkingLotList;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public void setParkingLotList(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }


}
