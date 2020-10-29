package com.example.upc.controller.searchParam;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class AdditiveSearchParam {
    private String enterprise;
    private String recordNo;
    private Date start;
    private Date end;
    private String publicity;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public String getRecordNo() {
        return recordNo;
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

    public String getPublicity() {
        return publicity;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }



    public void setPublicity(String publicity) {
        this.publicity = publicity;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }
}
