package com.example.upc.controller.searchParam;

import com.example.upc.controller.param.MorningAttendenceParam;

public class MorningAttendanceSearchParam extends MorningAttendenceParam {
    private String employeeName;

    private String bodyHealth;

    private String attendanceSituation;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getBodyHealth() {
        return bodyHealth;
    }

    public void setBodyHealth(String bodyHealth) {
        this.bodyHealth = bodyHealth == null ? null : bodyHealth.trim();
    }

    public String getAttendanceSituation() {
        return attendanceSituation;
    }

    public void setAttendanceSituation(String attendanceSituation) {
        this.attendanceSituation = attendanceSituation == null ? null : attendanceSituation.trim();
    }
}
