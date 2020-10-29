package com.example.upc.controller.param;

import com.example.upc.dataobject.FormatGoodsIn;

public class FormatGoodsSupParam extends FormatGoodsIn {
    String enterpriseName;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

}
