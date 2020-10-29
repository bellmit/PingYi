package com.example.upc.controller.param;

import com.example.upc.dataobject.ExamTopicBank;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/6/19 18:36
 */
public class ExamCaTopic extends ExamTopicBank {
      private String check;
      private Date submitTime;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}
