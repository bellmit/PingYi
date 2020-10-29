package com.example.upc.controller.param;

import com.example.upc.dataobject.ExamExam;

/**
 * @author zcc
 * @date 2019/10/22 11:01
 */
public class ExamCaExamParam extends ExamExam {
    private int qualifiedScore;
    private int totalScore;
    private int examTime;
    private int examCaId;

    public int getQualifiedScore() {
        return qualifiedScore;
    }

    public void setQualifiedScore(int qualifiedScore) {
        this.qualifiedScore = qualifiedScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getExamTime() {
        return examTime;
    }

    public void setExamTime(int examTime) {
        this.examTime = examTime;
    }

    public int getExamCaId() {
        return examCaId;
    }

    public void setExamCaId(int examCaId) {
        this.examCaId = examCaId;
    }
}
