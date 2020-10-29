package com.example.upc.controller.searchParam;

/**
 * @author zcc
 * @date 2019/9/10 22:01
 */
public class ExamTopicSearchParam {
    private String title;
    private Integer type;
    private Integer industryCategory;
    private Integer workType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }



    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(Integer industryCategory) {
        this.industryCategory = industryCategory;
    }
}
