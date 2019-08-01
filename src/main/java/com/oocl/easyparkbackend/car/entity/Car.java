package com.oocl.easyparkbackend.car.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "car_number",unique = true)
    @NotNull
    private String carNumber;

    public Car() {
    }

    public Car(Integer id, @NotNull String carNumber) {
        Id = id;
        this.carNumber = carNumber;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
