package com.example.upc.controller.param;

import com.example.upc.common.InsertGroup;
import com.example.upc.common.SearchGroup;
import com.example.upc.dataobject.MonthlyAdditionalAnswer;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.MonthlySelfcheckOptAnswer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class MonthlySelfCheckParam {

    private Integer id;

    private Integer enterpriseId;

    @NotNull(groups={SearchGroup.class},message = "搜索日期不能为空")
    @DateTimeFormat(pattern="yyyy")
    @JsonFormat(pattern="yyyy",timezone = "GMT+8")
    private Date searchTime;


    @NotNull(groups={InsertGroup.class},message = "检查日期不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date checkTime;

    @NotNull(groups={InsertGroup.class},message = "检查人员不能为空")
    private String checkStaff;

    @NotNull(groups={InsertGroup.class},message = "陪同检查人员不能为空")
    private String accompanyStaff;

    private String checkContent;

    private String existedProblem;

    private String rectifySituation;

    private String lastExistedProblem;

    private List<MonthlySelfCheckOptCategoryParam> monthlySelfCheckOptCategoryParamList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Date getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckStaff() {
        return checkStaff;
    }

    public void setCheckStaff(String checkStaff) {
        this.checkStaff = checkStaff;
    }

    public String getAccompanyStaff() {
        return accompanyStaff;
    }

    public void setAccompanyStaff(String accompanyStaff) {
        this.accompanyStaff = accompanyStaff;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getExistedProblem() {
        return existedProblem;
    }

    public void setExistedProblem(String existedProblem) {
        this.existedProblem = existedProblem;
    }

    public String getRectifySituation() {
        return rectifySituation;
    }

    public void setRectifySituation(String rectifySituation) {
        this.rectifySituation = rectifySituation;
    }

    public List<MonthlySelfCheckOptCategoryParam> getMonthlySelfCheckOptCategoryParamList() {
        return monthlySelfCheckOptCategoryParamList;
    }

    public void setMonthlySelfCheckOptCategoryParamList(List<MonthlySelfCheckOptCategoryParam> monthlySelfCheckOptCategoryParamList) {
        this.monthlySelfCheckOptCategoryParamList = monthlySelfCheckOptCategoryParamList;
    }

    public String getLastExistedProblem() {
        return lastExistedProblem;
    }

    public void setLastExistedProblem(String lastExistedProblem) {
        this.lastExistedProblem = lastExistedProblem;
    }
}
