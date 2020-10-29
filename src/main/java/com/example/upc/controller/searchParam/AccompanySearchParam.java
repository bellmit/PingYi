package com.example.upc.controller.searchParam;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

public class AccompanySearchParam {

    //结束时间
    private Date endTime;
    //开始时间
    private Date startTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
