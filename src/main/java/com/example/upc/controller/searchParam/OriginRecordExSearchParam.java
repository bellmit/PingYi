package com.example.upc.controller.searchParam;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 75186
 */
public class OriginRecordExSearchParam {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date start2;   //录入时间
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date end2;
    private String enterprise;
    private String area;
    private String type;
    private String name;
    private Date start1;
    private Date end1;
    private List<Integer> areaList;

    public Date getStart2() { return start2; }

    public Date getEnd2() { return end2; }
    public void setStart2(Date start2) {
        this.start2 = start2;
    }

    public void setEnd2(Date end2) {
        this.end2 = end2;
    }
    public String getEnterprise() {
        return enterprise;
    }

    public String getArea() {
        return area;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Date getStart1() {
        return start1;
    }

    public Date getEnd1() {
        return end1;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart1(Date start1) {
        this.start1 = start1;
    }

    public void setEnd1(Date end1) {
        this.end1 = end1;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }
}
