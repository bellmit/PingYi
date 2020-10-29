package com.example.upc.controller.searchParam;

/**
 * @author zcc
 * @date 2019/9/10 20:13
 */
public class ExamEnquirySearchParam {
    private String caName;
    private String examName;
    private Integer examResult;
    private String idNumber;
    private String companyName;
    private Integer industry;
    private Integer workType;

    public String getCaName() {
        return caName;
    }

    public void setCaName(String caName) {
        this.caName = caName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getExamResult() {
        return examResult;
    }

    public void setExamResult(Integer examResult) {
        this.examResult = examResult;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }
}
