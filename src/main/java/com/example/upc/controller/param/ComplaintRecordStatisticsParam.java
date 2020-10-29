package com.example.upc.controller.param;

import com.google.common.collect.Lists;

import java.util.List;

public class ComplaintRecordStatisticsParam {

    private Integer total;

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    private List<ComplaintRecordListParam> list1 = Lists.newArrayList();

    public List<ComplaintRecordListParam> getList1() {
        return list1;
    }

    public void setList1(List<ComplaintRecordListParam> list) {
        this.list1 = list;
    }

    private List<ComplaintRecordListParam> list2 = Lists.newArrayList();

    public void setList2(List<ComplaintRecordListParam> list2) {
        this.list2 = list2;
    }

    public List<ComplaintRecordListParam> getList2() {
        return list2;
    }

    private List<ComplaintRecordListParam> list3 = Lists.newArrayList();

    public void setList3(List<ComplaintRecordListParam> list3) {
        this.list3 = list3;
    }

    public List<ComplaintRecordListParam> getList3() {
        return list3;
    }

    private List<ComplaintRecordListParam> list4 = Lists.newArrayList();

    public void setList4(List<ComplaintRecordListParam> list4) {
        this.list4 = list4;
    }

    public List<ComplaintRecordListParam> getList4() {
        return list4;
    }
}
