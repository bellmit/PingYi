package com.example.upc.controller.param;

import com.example.upc.dataobject.BillReport;

public class BillReportParam extends BillReport{
    private String recordList;

    public String getRecordList() {
        return recordList;
    }

    public void setRecordList(String recordList) {
        this.recordList = recordList;
    }
}
