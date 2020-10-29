package com.example.upc.controller.param;

import com.example.upc.dataobject.ExamTrainMaterialWithBLOBs;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/6/19 17:11
 */
public class TrainCaMaterialParam extends ExamTrainMaterialWithBLOBs {
    private Float completionRate=0.00f;
    private Date startTime;
    private Date endTime;

    public Float getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Float completionRate) {
        this.completionRate = completionRate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
