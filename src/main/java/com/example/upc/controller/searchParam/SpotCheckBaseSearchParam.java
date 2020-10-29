package com.example.upc.controller.searchParam;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SpotCheckBaseSearchParam {
    private String company;
    private String name;
    private String result;
    private String person;
    @DateTimeFormat(pattern="yyyy-MM-dd 00:00:00")
    private Date start1;
    @DateTimeFormat(pattern="yyyy-MM-dd 00:00:00")
    private Date end1;
    private String team;
    private String orginization;

    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public String getPerson() {
        return person;
    }

    public Date getStart1() {
        return start1;
    }

    public Date getEnd1() {
        return end1;
    }

    public String getTeam() {
        return team;
    }

    public String getOrginization() {
        return orginization;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResult(String result) {
        this.result = result;
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

    public void setTeam(String team) {
        this.team = team;
    }

    public void setOrginization(String orginization) {
        this.orginization = orginization;
    }
}
