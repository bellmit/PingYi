package com.example.upc.controller.param;

import com.example.upc.dataobject.OnlineBusiness;
import com.google.common.collect.Lists;

import java.util.List;

public class OnlineBusinessParm extends OnlineBusiness {
    private List<Integer> splatList = Lists.newArrayList();

    public List<Integer> getSplatList(){ return splatList;}

    public void setSplatList(List<Integer> splatList){ this.splatList = splatList;}

}
