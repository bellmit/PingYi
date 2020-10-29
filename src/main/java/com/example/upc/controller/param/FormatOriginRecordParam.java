package com.example.upc.controller.param;

import com.example.upc.dataobject.FormatOriginRecord;
import com.example.upc.dataobject.FormatOriginRecordConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class FormatOriginRecordParam extends FormatOriginRecordEnParam {
    private List<FormatOriginRecordConfig> list = Lists.newArrayList();

    public List<FormatOriginRecordConfig> getList() {
        return list;
    }

    public void setList(List<FormatOriginRecordConfig> list) {
        this.list = list;
    }
}
