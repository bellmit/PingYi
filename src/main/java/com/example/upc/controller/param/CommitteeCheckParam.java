package com.example.upc.controller.param;

import com.example.upc.common.InsertGroup;
import com.example.upc.dataobject.CommitteCheckOptCategory;
import com.example.upc.dataobject.CommitteeAdditionalAnswer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * @author 董志涵
 */
@Data
@Getter
@Setter
public class CommitteeCheckParam extends CommitteeAdditionalAnswer {
    private Integer checkId;

    private Integer enterpriseId;

    @DateTimeFormat(pattern="yyyy")
    @JsonFormat(pattern="yyyy",timezone = "GMT+8")
    private Date searchTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date checkTime;

    private String recorder;

    private String representSign1;

    private String representSign2;

    private String representSign3;

    private List<CommitteCheckOptCategoryParam> committeCheckOptCategoryParamList;

    public List<CommitteCheckOptCategoryParam> getCommitteCheckOptCategoryParamList(){
        return committeCheckOptCategoryParamList;
    }

    public void setCommitteCheckOptCategoryParamList(List<CommitteCheckOptCategoryParam> committeCheckOptCategoryParamList){
        this.committeCheckOptCategoryParamList = committeCheckOptCategoryParamList;
    }

    public Integer getCheckId(){
        return checkId;
    }

    public void setCheckId(Integer checkId){
        this.checkId = checkId;
    }

    public Integer getEnterpriseId(){
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId){
        this.enterpriseId = enterpriseId;
    }
}
