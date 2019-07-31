package com.oocl.easyparkbackend.Employee.Vo;

public class AdjustVo {
    private String originPosition;
    private String newPosition;
    private int id;

    public AdjustVo() {
    }

    public AdjustVo(String originPosition, String newPosition, int id) {
        this.originPosition = originPosition;
        this.newPosition = newPosition;
        this.id = id;
    }

    public String getOriginPosition() {
        return originPosition;
    }

    public void setOriginPosition(String originPosition) {
        this.originPosition = originPosition;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
