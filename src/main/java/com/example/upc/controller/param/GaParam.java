package com.example.upc.controller.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GaParam {

    private Integer id;
    @NotBlank(message = "单位不能为空")
    private String unitName;
    @NotNull(message = "部门不能为空")
    private Integer department;
    @NotBlank(message = "姓名不能为空")
    private String name;
    private Integer sexy;
    private Integer job;
    private Integer type;
    private String idNumber;
    private String enforce;
    @NotBlank(message = "移动电话不能为空")
    private String mobilePhone;
    private String officePhone;
    @Min(value = 1,message = "序号必须是大于1的数字")
    private Integer number;
    @NotBlank(message = "工作电话不能为空")
    private String workPhone;
    private String category;
    private Integer isStop;
    private String photo;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getSexy() {
        return sexy;
    }

    public void setSexy(Integer sexy) {
        this.sexy = sexy;
    }

    public Integer getJob() {
        return job;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEnforce() {
        return enforce;
    }

    public void setEnforce(String enforce) {
        this.enforce = enforce;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
