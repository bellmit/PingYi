package com.example.upc.controller.searchParam;


public class OriginExtraSearchParam {
    private String materialname;
    private String materialcategory;
    private String brand;
    private int listId;

    public String getMaterialname() {
        return materialname;
    }

    public String getMaterialcategory() {
        return materialcategory;
    }

    public String getBrand() {
        return brand;
    }

    public int getListId(){return listId;}

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public void setMaterialcategory(String materialcategory) {
        this.materialcategory = materialcategory;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setListId(int listId) {this.listId=listId;}
}
