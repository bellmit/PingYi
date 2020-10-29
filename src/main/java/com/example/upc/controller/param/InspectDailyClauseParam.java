package com.example.upc.controller.param;

import com.example.upc.dataobject.InspectClauseConf;

/**
 * @author zcc
 * @date 2019/5/20 23:06
 */
public class InspectDailyClauseParam extends InspectClauseConf {
    private Integer largeClassId;
    private String largeClassName;
    private Integer result=0;
    private String resultRemark;
    private String document;

    public Integer getLargeClassId() {
        return largeClassId;
    }

    public void setLargeClassId(Integer largeClassId) {
        this.largeClassId = largeClassId;
    }

    public String getLargeClassName() {
        return largeClassName;
    }

    public void setLargeClassName(String largeClassName) {
        this.largeClassName = largeClassName;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getResultRemark() {
        return resultRemark;
    }

    public void setResultRemark(String resultRemark) {
        this.resultRemark = resultRemark;
    }
}
