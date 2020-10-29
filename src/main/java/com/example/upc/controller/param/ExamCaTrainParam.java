package com.example.upc.controller.param;

import com.example.upc.dataobject.ExamTrainCourse;

/**
 * @author zcc
 * @date 2019/10/19 16:50
 */
public class ExamCaTrainParam extends ExamTrainCourse {
    private int materialNumber;
    private int videoMaterialNumber;
    private int videoCourseScore;
    private int bookCourseScore;
    private float caScore;
    private float courseScore;

    public int getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(int materialNumber) {
        this.materialNumber = materialNumber;
    }

    public int getVideoMaterialNumber() {
        return videoMaterialNumber;
    }

    public void setVideoMaterialNumber(int videoMaterialNumber) {
        this.videoMaterialNumber = videoMaterialNumber;
    }

    public int getVideoCourseScore() {
        return videoCourseScore;
    }

    public void setVideoCourseScore(int videoCourseScore) {
        this.videoCourseScore = videoCourseScore;
    }

    public int getBookCourseScore() {
        return bookCourseScore;
    }

    public void setBookCourseScore(int bookCourseScore) {
        this.bookCourseScore = bookCourseScore;
    }

    public float getCaScore() {
        return caScore;
    }

    public void setCaScore(float caScore) {
        this.caScore = caScore;
    }

    public float getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(float courseScore) {
        this.courseScore = courseScore;
    }
}
