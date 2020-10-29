package com.example.upc.controller.searchParam;

import java.util.List;

public class QuickCheckEnterpriseSearchParam {
    private String enterprise;
    private String nickname;
    private List<Integer> areaList;

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Integer> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Integer> areaList) {
        this.areaList = areaList;
    }

}
