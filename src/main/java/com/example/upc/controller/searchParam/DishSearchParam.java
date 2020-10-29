package com.example.upc.controller.searchParam;

import java.security.PrivateKey;
import java.util.List;

public class DishSearchParam {
    private String name;
    private String number;
    private String type;
    private Float price;
    private List<Integer> areaList;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price ;
    }
    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }
}
