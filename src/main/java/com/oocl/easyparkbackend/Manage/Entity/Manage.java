package com.oocl.easyparkbackend.Manage.Entity;

import com.oocl.easyparkbackend.Employee.Entity.Clerk;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "manage")
public class Manage  extends Clerk {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;
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

    public Manage(Integer id, @NotNull String username, @NotNull String password, @NotNull String name, @NotNull String phoneNumber, @NotNull Integer status, @NotNull String email) {
        Id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.email = email;
    }

    public Manage() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
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
}
