package com.example.upc.controller.searchParam;

import java.util.Date;
import java.util.List;

public class PictureSearchParam {
    private String enterprise;
    private String kind;
    private Date start;
    private Date end;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getKind() {
        return kind;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }
}