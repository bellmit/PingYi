package com.example.upc.controller.searchParam;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QuickCheckBaseSearchParam {
    private String company;
    private String team;
    private String market;
    @DateTimeFormat(pattern="yyyy-MM-dd 00:00:00")
    private Date start1;
    @DateTimeFormat(pattern="yyyy-MM-dd 00:00:00")
    private Date end1;

    public String getCompany() {
        return company;
    }

    public String getTeam() {
        return team;
    }

    public Date getStart1() {
        return start1;
    }

    public Date getEnd1() {
        return end1;
    }

    public String getMarket() {
        return market;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setStart1(Date start1) {
        this.start1 = start1;
    }

    public void setEnd1(Date end1) {
        this.end1 = end1;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
