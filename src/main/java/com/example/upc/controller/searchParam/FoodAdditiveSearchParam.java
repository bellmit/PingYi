package com.example.upc.controller.searchParam;

import com.example.upc.common.InsertGroup;
import com.example.upc.common.SearchGroup;
import com.example.upc.dataobject.FoodAdditive;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FoodAdditiveSearchParam extends FoodAdditive {
    @NotNull(groups={SearchGroup.class},message = "开始日期不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date startDate;

    @NotNull(groups={SearchGroup.class},message = "结束日期不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
