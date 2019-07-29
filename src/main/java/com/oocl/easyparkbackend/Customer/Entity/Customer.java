package com.oocl.easyparkbackend.Customer.Entity;

import com.oocl.easyparkbackend.Car.Entity.Car;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "phone", unique = true)
    @NotNull
    private String phone;

    @OneToMany
    private List<Car> carList;

    public Customer() {
    }

    public Customer(Integer id, @NotNull String name, @NotNull String username, @NotNull String password, @NotNull String phone, List<Car> carList) {
        Id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.carList = carList;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }
}
