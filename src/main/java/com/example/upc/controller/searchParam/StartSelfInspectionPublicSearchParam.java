package com.example.upc.controller.searchParam;

import com.example.upc.dataobject.InspectionPosition;
import com.example.upc.dataobject.StartSelfInspection;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;

public class StartSelfInspectionPublicSearchParam extends InspectionPosition {
    //private List<String> picList = Lists.newArrayList();

    private  List<StartSelfInspection> picList = Lists.newArrayList();

    public void setPicList( List<StartSelfInspection> picList){this.picList=picList; }
    public  List<StartSelfInspection> getPicList(){ return picList;}
}
