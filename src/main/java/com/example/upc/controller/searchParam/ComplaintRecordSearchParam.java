package com.example.upc.controller.searchParam;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.Format;
import java.util.Date;

public class ComplaintRecordSearchParam {
    String number;
    String problemTwo;
    String information;
    String complaintPerson;
    String enterpriseName;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date start1;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date end1;
    String department;
    Integer state;

    public String getNumber() {
        return number;
    }

    public String getProblemTwo() {
        return problemTwo;
    }

    public String getInformation() {
        return information;
    }

    public String getComplaintPerson() {
        return complaintPerson;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public Date getStart1() {
        return start1;
    }

    public Date getEnd1() {
        return end1;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getState() {
        return state;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setProblemTwo(String problemTwo) {
        this.problemTwo = problemTwo;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setComplaintPerson(String complaintPerson) {
        this.complaintPerson = complaintPerson;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public void setStart1(Date start1) {
        this.start1 = start1;
    }

    public void setEnd1(Date end1) {
        this.end1 = end1;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
