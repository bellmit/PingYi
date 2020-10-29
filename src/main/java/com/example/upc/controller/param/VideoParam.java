package com.example.upc.controller.param;

import com.example.upc.dataobject.VideoConfigEx;
import com.example.upc.dataobject.VideoParent;
import com.google.common.collect.Lists;

import java.util.List;

public class VideoParam extends VideoParent {
    private List<VideoConfigEx> list = Lists.newArrayList();

    public List<VideoConfigEx> getList() {
        return list;
    }

    public void setList(List<VideoConfigEx> list) {
        this.list = list;
    }
}
