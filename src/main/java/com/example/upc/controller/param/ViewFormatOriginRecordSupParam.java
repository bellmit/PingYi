package com.example.upc.controller.param;

import com.example.upc.dataobject.ViewFormatOriginRecord;

public class ViewFormatOriginRecordSupParam extends ViewFormatOriginRecord {
    private String enterpriseName;
    private String areaName;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
