package com.example.upc.controller.searchParam;

import com.example.upc.dataobject.ExamSubject;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/5/13 10:02
 */
public class ExamSubjectSearchParam extends ExamSubject {

    private Integer industryCategory;
    private Integer workTypeId;
    private String industryName;
    private String workTypeName;
    private Integer topicNumber=0;

    public Integer getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(Integer industryCategory) {
        this.industryCategory = industryCategory;
    }

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public Integer getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(Integer topicNumber) {
        this.topicNumber = topicNumber;
    }
}
