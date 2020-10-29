package com.example.upc.controller.param;

import com.example.upc.dataobject.FormatAdditive;
import com.example.upc.dataobject.FormatAdditiveConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class FormatAdditiveParam extends FormatAdditive {

    private List<FormatAdditiveConfig> list = Lists.newArrayList();

    public List<FormatAdditiveConfig> getList() {
        return list;
    }

    public void setList(List<FormatAdditiveConfig> list) {
        this.list = list;
    }
}
