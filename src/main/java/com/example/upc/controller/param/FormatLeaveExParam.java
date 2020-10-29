package com.example.upc.controller.param;

import java.util.Date;

public class FormatLeaveExParam {
    private Integer parentId;
    private Integer seq;
    private String name;
    private String material1;
    private String state;
    private String num;
    private String operator;
    private String operatorIp;
    private Date operatorTime;

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterial1(String material1) {
        this.material1 = material1;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getMaterial1() {
        return material1;
    }

    public String getState() {
        return state;
    }

    public String getNum() {
        return num;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }
}
