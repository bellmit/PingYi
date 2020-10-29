package com.example.upc.controller.param;

import com.example.upc.dataobject.*;
import com.google.common.collect.Lists;

import java.util.List;

public class SpotCheckPercentParam {
    private List<ViewSpotCheckTotalPercent> list1 = Lists.newArrayList();

    public List<ViewSpotCheckTotalPercent> getList1() {
        return list1;
    }

    public void setList1(List<ViewSpotCheckTotalPercent> list) {
        this.list1 = list;
    }


//所属所队和环节表
    private List<ViewSpotCheckTeamResult> list2 = Lists.newArrayList();

    public List<ViewSpotCheckTeamResult> getList2() {
        return list2;
    }

    public void setList2(List<ViewSpotCheckTeamResult> list) {
        this.list2 = list;
    }

    private List<ViewSpotCheckStepResult> list3 = Lists.newArrayList();

    public List<ViewSpotCheckStepResult> getList3() {
        return list3;
    }

    public void setList3(List<ViewSpotCheckStepResult> list) {
        this.list3 = list;
    }


//类别和样品表
    private List<ViewSpotCheckTypeResult> list4 = Lists.newArrayList();

    public List<ViewSpotCheckTypeResult> getList4() {
        return list4;
    }

    public void setList4(List<ViewSpotCheckTypeResult> list) {
        this.list4= list;
    }


    //花费总表
    private List<ViewSpotCheckMoney> list5 = Lists.newArrayList();

    public List<ViewSpotCheckMoney> getList5() {
        return list5;
    }

    public void setList5(List<ViewSpotCheckMoney> list) {
        this.list5= list;
    }


    //分机构费用总表
    private List<ViewSpotCheckOrgResult> list6 = Lists.newArrayList();

    public List<ViewSpotCheckOrgResult> getList6() {
        return list6;
    }

    public void setList6(List<ViewSpotCheckOrgResult> list) {
        this.list6= list;
    }


    //样品不合格数topten
    private List<ViewSpotCheckSampleTopTen> list7 = Lists.newArrayList();

    public List<ViewSpotCheckSampleTopTen> getList7() {
        return list7;
    }

    public void setList7(List<ViewSpotCheckSampleTopTen> list) {
        this.list7= list;
    }

    //样品花费topten
    private List<ViewSpotCheckNameTopTen> list8 = Lists.newArrayList();

    public List<ViewSpotCheckNameTopTen> getList8() {
        return list8;
    }

    public void setList8(List<ViewSpotCheckNameTopTen> list) {
        this.list8= list;
    }


    //样品花费topten
    private List<ViewSpotCheckTeamSumResult> list9 = Lists.newArrayList();

    public List<ViewSpotCheckTeamSumResult> getList9() {
        return list9;
    }

    public void setList9(List<ViewSpotCheckTeamSumResult> list) {
        this.list9= list;
    }



    //样品花费topten
    private List<ViewSpotCheckStepSumResult> list10 = Lists.newArrayList();

    public List<ViewSpotCheckStepSumResult> getList10() {
        return list10;
    }

    public void setList10(List<ViewSpotCheckStepSumResult> list) {
        this.list10= list;
    }




}
