package com.example.upc.controller.param;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class FormatGoodsParam {
    private Integer id;
    private String type;
    @NotBlank(message = "产品名称不能为空")
    private String name;
    @NotBlank(message = "供应商不能为空")
    private String supplier;
    @NotNull(message = "进/出货日期不能为空")
    private Date time;
    @NotBlank(message = "有效期不能为空")
    private String day;
    @NotNull(message = "数量不能为空")
    private float num;
    @NotBlank(message = "单位不能为空")
    private String goodsType;
    @NotNull(message = "生产日期不能为空")
    private Date date;
    private String brand;
    private String weight;
    private String manufacturer;
    private String person;
    private String document;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public String getGoodsType() {
        return goodsType;
    }
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }
    public String getPerson() {
        return person;
    }
    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document == null ? null : document.trim();
    }
}
