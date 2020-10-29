package com.example.upc.controller.param;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class FormatSupplierParam {
    private Integer id;
    private Integer enterpriseId;
    private String enterpriseName;
    private Integer areaId;
    private String stype;
    @NotBlank(message = "地址不能为空")
    private String address;
    @NotBlank(message = "负责人不能为空")
    private String principal;
    @NotBlank(message = "类型不能为空")
    private String type;
    private String organ;
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    @NotBlank(message = "供应商名称不能为空")
    private String name;
    @NotBlank(message = "社会信用代码证号不能为空")
    private String number;
    @NotBlank(message = "营业执照号不能为空")
    private String license;
    @NotBlank(message = "许可范围不能为空")
    private String supplierSize;
    @NotBlank(message = "联系人不能为空")
    private String person;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "有效期限起始不能为空")
    private Date start;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "有效期限终止不能为空")
    private Date end;
    private String document;

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getStype() {
        return stype;
    }
    public void setStype(String stype) {
        this.stype = stype == null ? null : stype.trim();
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    public String getOrgan() {
        return organ;
    }
    public void setOrgan(String organ) {
        this.organ = organ == null ? null : organ.trim();
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
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
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }
    public String getSupplierSize() {
        return supplierSize;
    }
    public void setSupplierSize(String supplierSize) {
        this.supplierSize = supplierSize == null ? null : supplierSize.trim();
    }
    public String getPerson() {
        return person;
    }
    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }
    public Date getStart() {
        return start;
    }
    public void setStart(Date start) {
        this.start = start;
    }
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document == null ? null : document.trim();
    }
}
