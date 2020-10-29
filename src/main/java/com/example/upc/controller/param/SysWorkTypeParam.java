package com.example.upc.controller.param;

import com.example.upc.dataobject.SysWorkType;

/**
 * @author zcc
 * @date 2019/5/13 22:16
 */
public class SysWorkTypeParam extends SysWorkType {
    private String industryName;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
