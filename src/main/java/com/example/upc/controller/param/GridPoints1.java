package com.example.upc.controller.param;

import java.util.Date;

public class GridPoints1 {
    private Integer id;
    private Integer area;
    private Integer grid;
    private String permission_type;
    private String registered_address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }
    public Integer getGrid() {
        return grid;
    }

    public void setGrid(Integer grid) {
        this.grid = grid;
    }

    public String getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(String permission_type) { this.permission_type = permission_type == null ? null : permission_type.trim(); }

    public String getRegistered_address () {
        return registered_address;
    }

    public void setRegistered_address(String registered_address) {
        this.registered_address = registered_address;
    }
}