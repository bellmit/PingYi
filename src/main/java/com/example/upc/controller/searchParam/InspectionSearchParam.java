package com.example.upc.controller.searchParam;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class InspectionSearchParam {
    private String positionArea;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "日期不能为空")
    private Date start1;
    private Date end1;


    public String getPositionArea() {
        return positionArea;
    }

    public Date getStart1() {
        return start1;
    }

    public Date getEnd1() {return end1;}


    public void setPositionArea(String positionArea) {
        this.positionArea = positionArea;
    }

    public void setStart1(Date start1) {
        this.start1 = start1;
    }

    public void setEnd1(Date end1) {this.end1 = end1;}
}


