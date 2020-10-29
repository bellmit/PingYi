package com.example.upc.controller.param;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class FormatWasteParam {
    private Integer id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "处置日期不能为空")
    private Date disposaltime;
    @NotBlank(message = "种类不能为空")
    private String kind;
    @NotBlank(message = "数量不能为空")
    private String number;
    @NotBlank(message = "处置人不能为空")
    private String disposalperson;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "登记日期不能为空")
    private Date registrationtime;
    @NotBlank(message = "回收企业不能为空")
    private String recyclingenterprises;
    @NotBlank(message = "回收人不能为空")
    private String recycler;
    private String extra;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getDisposaltime() {
        return disposaltime;
    }
    public void setDisposaltime(Date disposaltime) {
        this.disposaltime = disposaltime;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }
    public String getDisposalperson() {
        return disposalperson;
    }
    public void setDisposalperson(String disposalperson) {
        this.disposalperson = disposalperson == null ? null : disposalperson.trim();
    }
    public Date getRegistrationtime() {
        return registrationtime;
    }
    public void setRegistrationtime(Date registrationtime) {
        this.registrationtime = registrationtime;
    }
    public String getRecyclingenterprises() {
        return recyclingenterprises;
    }
    public void setRecyclingenterprises(String recyclingenterprises) {
        this.recyclingenterprises = recyclingenterprises == null ? null : recyclingenterprises.trim();
    }
    public String getRecycler() {
        return recycler;
    }
    public void setRecycler(String recycler) {
        this.recycler = recycler == null ? null : recycler.trim();
    }
    public String getExtra() {
        return extra;
    }
    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }
}
