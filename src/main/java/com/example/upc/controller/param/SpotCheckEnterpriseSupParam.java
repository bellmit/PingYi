package com.example.upc.controller.param;

import com.example.upc.dataobject.SpotCheckEnterprise;

public class SpotCheckEnterpriseSupParam extends SpotCheckEnterprise {
    String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }
}
