package com.example.upc.controller.searchParam;


import com.example.upc.dataobject.FormatDisinfection;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class DisinfectionSearchParam extends FormatDisinfection {
    private String enterprise;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date start;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date end;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
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

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }


}
