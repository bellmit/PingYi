package com.example.upc.controller.param;

import com.example.upc.dataobject.BillReport;
import com.example.upc.dataobject.FormatOriginRecordEx;

import java.util.List;

/**
 * @author 75186
 */
public class FormatOriginRecordExListParam extends FormatOriginRecordEx {
    private String enterpriseName;
    private String areaName;
    private List<BillListParam> billList;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<BillListParam> getBillList() {
        return billList;
    }

    public void setBillList(List<BillListParam> billList) {
        this.billList = billList;
    }
}
