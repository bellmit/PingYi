package com.example.upc.controller.param;

import com.example.upc.dataobject.FormatLeaveMini;
import com.example.upc.dataobject.FormatLeaveSample;
import com.google.common.collect.Lists;

import java.util.List;

public class FormatLeaveMiniParam extends FormatLeaveSample {
    private List<FormatLeaveMini> list= Lists.newArrayList();

    public List<FormatLeaveMini> getList() {
        return list;
    }

    public void setList(List<FormatLeaveMini> list) {
        this.list = list;
    }
}
