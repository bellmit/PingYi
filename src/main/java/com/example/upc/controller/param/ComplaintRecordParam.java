package com.example.upc.controller.param;

import com.example.upc.dataobject.ComplaintRecord;

public class ComplaintRecordParam extends ComplaintRecord {
    String deptName;
    String leaderName;

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }
}
