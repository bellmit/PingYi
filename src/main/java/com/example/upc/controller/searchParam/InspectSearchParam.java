package com.example.upc.controller.searchParam;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/10/3 21:35
 */
public class InspectSearchParam {
    private String checkDate;
    private Integer checkType;
    private Integer region;
    private String checkObject;
    private String supervisor;
    private Integer status;
    private Integer checkFrequence;
    private String checkResult;
    private String resultProcess;
    private Integer checkCount;


    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getCheckObject() {
        return checkObject;
    }

    public void setCheckObject(String checkObject) {
        this.checkObject = checkObject;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCheckFrequence() {
        return checkFrequence;
    }

    public void setCheckFrequence(Integer checkFrequence) {
        this.checkFrequence = checkFrequence;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getResultProcess() {
        return resultProcess;
    }

    public void setResultProcess(String resultProcess) {
        this.resultProcess = resultProcess;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }
}
