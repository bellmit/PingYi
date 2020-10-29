package com.example.upc.controller.param;

import com.example.upc.dataobject.InspectClauseConf;

/**
 * @author zcc
 * @date 2019/5/18 20:09
 */
public class InspectClauseConfParam extends InspectClauseConf {
    private Integer checkItem;
    private Integer industry;
    private String industryName;
    private String largeClassName;

    public Integer getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(Integer checkItem) {
        this.checkItem = checkItem;
    }

    public Integer getIndustry() {
        return industry;
    }
    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getLargeClassName() {
        return largeClassName;
    }

    public void setLargeClassName(String largeClassName) {
        this.largeClassName = largeClassName;
    }
}
