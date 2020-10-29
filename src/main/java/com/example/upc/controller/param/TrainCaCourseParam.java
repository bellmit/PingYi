package com.example.upc.controller.param;

public class TrainCaCourseParam {
    private Integer caId;
    private Integer courseId;
    private Integer courseType;
    private String courseName;
    private float allPeriod;
    private float nowPeriod;

    public Integer getCaId() {
        return caId;
    }

    public void setCaId(Integer caId) {
        this.caId = caId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getAllPeriod() {
        return allPeriod;
    }

    public void setAllPeriod(float allPeriod) {
        this.allPeriod = allPeriod;
    }

    public float getNowPeriod() {
        return nowPeriod;
    }

    public void setNowPeriod(float nowPeriod) {
        this.nowPeriod = nowPeriod;
    }
}
