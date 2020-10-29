package com.example.upc.controller.searchParam;

import com.example.upc.dataobject.BillReport;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class BillReportSearchParam extends BillReport {
    //插入更新时用
    private List<Integer> idList = Lists.newArrayList();
    //结束时间
    private Date endTime;
    //查看BillReport
    private String billList;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBillList() {
        return billList;
    }

    public void setBillList(String billList) {
        this.billList = billList;
    }
}
