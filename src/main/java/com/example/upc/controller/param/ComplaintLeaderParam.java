package com.example.upc.controller.param;

import com.example.upc.dataobject.SupervisionGa;

public class ComplaintLeaderParam extends SupervisionGa {
    String deptName;

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }
}
