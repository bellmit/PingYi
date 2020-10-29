package com.example.upc.controller.param;

import com.example.upc.dataobject.FormatOriginRecordEx;

public class FormatOriginRecordExEnParam extends FormatOriginRecordEx {
    private String enterpriseName;
    private String areaName;
    private String billList;

    public String getBillList() {
        return billList;
    }

    public void setBillList(String billList) {
        this.billList = billList;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
