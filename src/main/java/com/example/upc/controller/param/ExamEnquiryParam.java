package com.example.upc.controller.param;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/7/3 16:35
 */
public class ExamEnquiryParam {
    private int id;
    private String name;
    private String idNumber;
    private String telephone;
    private int sexy;
    private int industry;
    private int workType;
    private String companyName;
    private String photo;
    private float examScore;
    private Integer examResult;
    private String examName;
    private int examId;
    private int subjectId;
    private Date examDate;
    private String electronicNumber;
    private String creditNumber;
    private String businessAddress;
    private Float totalScore;
    private Float qualifiedScore;
    private Integer examTime;

    public String getElectronicNumber() {
        return electronicNumber;
    }

    public void setElectronicNumber(String electronicNumber) {
        this.electronicNumber = electronicNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getSexy() {
        return sexy;
    }

    public void setSexy(int sexy) {
        this.sexy = sexy;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getExamScore() {
        return examScore;
    }

    public void setExamScore(float examScore) {
        this.examScore = examScore;
    }

    public Integer getExamResult() {
        return examResult;
    }

    public void setExamResult(Integer examResult) {
        this.examResult = examResult;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCreditNumber() {
        return creditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        this.creditNumber = creditNumber;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public Float getQualifiedScore() {
        return qualifiedScore;
    }

    public void setQualifiedScore(Float qualifiedScore) {
        this.qualifiedScore = qualifiedScore;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }
}
