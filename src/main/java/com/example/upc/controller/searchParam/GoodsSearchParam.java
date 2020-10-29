package com.example.upc.controller.searchParam;

import java.util.Date;
import java.util.List;

public class GoodsSearchParam {
    private String enterprise;
    private String type;
    private String name;
    private String supplier;
    private List<Integer> areaList;

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
