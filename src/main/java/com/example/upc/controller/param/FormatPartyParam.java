package com.example.upc.controller.param;

import com.example.upc.dataobject.FormatParty;
import com.example.upc.dataobject.FormatPartyConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class FormatPartyParam extends FormatParty {
    private List<FormatPartyConfig> list = Lists.newArrayList();

    public List<FormatPartyConfig> getList() {
        return list;
    }

    public void setList(List<FormatPartyConfig> list) {
        this.list = list;
    }
    }
