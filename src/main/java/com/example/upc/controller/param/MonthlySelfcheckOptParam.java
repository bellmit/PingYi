package com.example.upc.controller.param;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class MonthlySelfcheckOptParam {
    private Integer optId;

    private String optTopic;

    private Integer optIndex;

    private String optAnswer;

    private String operator;

    private String operatorIp;

    private Date operatorTime;

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public String getOptTopic() {
        return optTopic;
    }

    public void setOptTopic(String optTopic) {
        this.optTopic = optTopic;
    }

    public Integer getOptIndex() {
        return optIndex;
    }

    public void setOptIndex(Integer optIndex) {
        this.optIndex = optIndex;
    }

    public String getOptAnswer() {
        return optAnswer;
    }

    public void setOptAnswer(String optAnswer) {
        this.optAnswer = optAnswer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}
