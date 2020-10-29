package com.example.upc.controller.param;

import com.example.upc.dataobject.VideoConfig;

/**
 * @author zcc
 * @date 2019/9/6 15:15
 */
public class VideoConfigParam extends VideoConfig {
    private String enterpriseName;
    private String areaName;
    private String level;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
