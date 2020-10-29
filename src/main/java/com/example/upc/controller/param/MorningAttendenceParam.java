package com.example.upc.controller.param;

import com.example.upc.dataobject.MorningAttendanceInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class MorningAttendenceParam {
    private Integer enterpriseId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date checkTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date endDate;
    private String recorderName;
    private Integer attendanceId;
    List<MorningAttendanceInfo> morningAttendanceInfosList = Lists.newArrayList();;

    public List<MorningAttendanceInfo> getMorningAttendanceInfosList() {
        return morningAttendanceInfosList;
    }

    public void setMorningAttendanceInfosList(List<MorningAttendanceInfo> morningAttendanceInfosList) {
        this.morningAttendanceInfosList = morningAttendanceInfosList;
    }
    public Integer getAttendanceId() {
        return attendanceId;
    }
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }
    public Integer getEnterpriseId() {
        return enterpriseId;
    }
    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
    public Date getCheckTime() {
        return checkTime;
    }
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    public String getRecorderName() {
        return recorderName;
    }
    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName == null ? null : recorderName.trim();
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
