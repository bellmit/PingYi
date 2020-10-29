package com.example.upc.controller.searchParam;

import com.example.upc.dataobject.UserEnterpriseVote;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseVoteSearchParam extends UserEnterpriseVote {

    private String startTime;

    private List<Integer> problemIdList = Lists.newArrayList();
    public  void setProblemIdList(List<Integer> problemIdList){
        this.problemIdList = problemIdList;
    }
    public List<Integer> getProblemIdList() {
        return problemIdList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
