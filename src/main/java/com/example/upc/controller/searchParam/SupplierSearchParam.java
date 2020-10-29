package com.example.upc.controller.searchParam;

import java.util.List;

public class SupplierSearchParam {
    private String stype;
    private String number;
    private String address;
    private String name;
    private List<Integer> areaList;

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }

    public String getStype() {
        return stype;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
}
