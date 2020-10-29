package com.example.upc.controller.searchParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class LeaveSearchParam {
    private String enterprise;
    private String meal;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date start;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date end;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public String getMeal() {
        return meal;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }
}
