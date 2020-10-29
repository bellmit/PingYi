package com.example.upc.controller.param;

import com.example.upc.dataobject.ExamTrainCourse;

/**
 * @author zcc
 * @date 2019/5/13 22:58
 */
public class TrainCourseParam extends ExamTrainCourse {
    private String industryName;
    private String workTypeName;
    private Float courseScore;
    private Integer materialNumber;
    private Integer videoMaterialNumber;

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

    public Float getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Float courseScore) {
        this.courseScore = courseScore;
    }

    public Integer getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(Integer materialNumber) {
        this.materialNumber = materialNumber;
    }

    public Integer getVideoMaterialNumber() {
        return videoMaterialNumber;
    }

    public void setVideoMaterialNumber(Integer videoMaterialNumber) {
        this.videoMaterialNumber = videoMaterialNumber;
    }
}
