package com.oocl.easyparkbackend.Manage.Vo;

import java.util.List;

public class BoysLotVo {
    private List<String> list;
    private Integer id;

    public BoysLotVo() {
    }

    public BoysLotVo(List<String> list, Integer id) {
        this.list = list;
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
