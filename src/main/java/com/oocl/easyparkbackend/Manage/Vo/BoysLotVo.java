package com.oocl.easyparkbackend.Manage.Vo;

import java.util.List;

public class BoysLotVo {
    private List<Integer> list;
    private Integer id;

    public BoysLotVo() {
    }

    public BoysLotVo(List<Integer> list, Integer id) {
        this.list = list;
        this.id = id;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
