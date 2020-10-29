package com.example.upc.controller.searchParam;

import java.util.List;

public class PartySearchParam {
    private String enterprise;
    private String person;
    private String record;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public String getPerson() {
        return person;
    }

    public String getRecord() {
        return record;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setRecord(String record) {
        this.record = record;
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
