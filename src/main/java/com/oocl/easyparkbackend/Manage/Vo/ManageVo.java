package com.oocl.easyparkbackend.Manage.Vo;

import com.oocl.easyparkbackend.Manage.Entity.Manage;

import java.util.List;

public class ManageVo {

    private Manage manage;

    public ManageVo(Manage manage) {
        this.manage = manage;
    }

    public Integer getId() {
        return this.manage.getId();
    }

    public String getUsername() {
        return this.manage.getUsername();
    }

    public String getName() {
        return this.manage.getName();
    }

    public String getPhoneNumber() {
        return this.manage.getPhoneNumber();
    }

    public Integer getStatus() {
        return this.manage.getStatus();
    }

    public String getEmail() {
        return this.manage.getEmail();
    }

    public List<String> getRoles() {
        return manage.roles();
    }
}
