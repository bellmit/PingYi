package com.example.upc.controller.param;

import com.example.upc.dataobject.InspectSignConf;

/**
 * @author zcc
 * @date 2019/9/14 17:35
 */
public class InspectSignParam extends InspectSignConf {
    private String deptName;
    private String gaName;
    private String gaUnit;
    private String gaEnforce;
    private String gaPhone;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getGaName() {
        return gaName;
    }

    public void setGaName(String gaName) {
        this.gaName = gaName;
    }

    public String getGaUnit() {
        return gaUnit;
    }

    public void setGaUnit(String gaUnit) {
        this.gaUnit = gaUnit;
    }

    public String getGaEnforce() {
        return gaEnforce;
    }

    public void setGaEnforce(String gaEnforce) {
        this.gaEnforce = gaEnforce;
    }

    public String getGaPhone() {
        return gaPhone;
    }

    public void setGaPhone(String gaPhone) {
        this.gaPhone = gaPhone;
    }
}
