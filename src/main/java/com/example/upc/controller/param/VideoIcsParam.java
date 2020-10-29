package com.example.upc.controller.param;

import com.example.upc.dataobject.VideoConfigIcs;
import com.example.upc.dataobject.VideoParentIcs;
import com.google.common.collect.Lists;

import java.util.List;

public class VideoIcsParam extends VideoParentIcs {
    private List<VideoConfigIcs> list = Lists.newArrayList();

    public List<VideoConfigIcs> getList() {
        return list;
    }

    public void setList(List<VideoConfigIcs> list) {
        this.list = list;
    }
}
