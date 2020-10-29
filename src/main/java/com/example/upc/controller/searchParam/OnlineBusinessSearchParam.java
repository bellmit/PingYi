package com.example.upc.controller.searchParam;

import java.util.List;

public class OnlineBusinessSearchParam {

    int enterpriseId;
    private List<Integer> splatList;
    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public List<Integer> getSplatList() {
        return splatList;
    }

    public void setSplatList(List<Integer> splatList) {
        this.splatList = splatList;
    }
}
