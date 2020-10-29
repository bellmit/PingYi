package com.example.upc.controller.searchParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class WasteSearchParam {
    private String enterprise;
    private String kind;
    private String person;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date start1;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date end1;
    private Date start2;
    private Date end2;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getKind() {
        return kind;
    }

    public String getPerson() {
        return person;
    }

    public Date getStart1() { return start1; }

    public Date getEnd1() { return end1; }

    public Date getStart2() {
        return start2;
    }

    public Date getEnd2() {
        return end2;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setStart1(Date start1) {
        this.start1 = start1;
    }

    public void setEnd1(Date end1) {
        this.end1 = end1;
    }

    public void setStart2(Date start2) {
        this.start2 = start2;
    }

    public void setEnd2(Date end2) {
        this.end2 = end2;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }
}
