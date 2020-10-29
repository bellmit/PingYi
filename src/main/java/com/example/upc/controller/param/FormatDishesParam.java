package com.example.upc.controller.param;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FormatDishesParam {
    private Integer id;
    private Integer unitId;
    private Integer areaId;
    @NotBlank(message = "菜品编号不能为空")
    private String number;
    @NotBlank(message = "菜品名称不能为空")
    private String name;
    @NotNull(message = "价格不能为空")
    @Min(0)
    private Float price;
    @NotBlank(message = "菜品类别不能为空")
    private String type;
    @NotBlank(message = "菜品介绍不能为空")
    private String remark;
    private String photo;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }
}
