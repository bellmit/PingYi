package com.example.upc.controller.param;

import com.example.upc.dataobject.SupervisionEnterprise;

public class EnterpriseListResultParam extends SupervisionEnterprise {
    String areaName;

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }
}
