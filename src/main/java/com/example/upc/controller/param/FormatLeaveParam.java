package com.example.upc.controller.param;

import com.example.upc.dataobject.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class FormatLeaveParam extends FormatLeaveSample {
    private List<FormatLeaveCoolCourse> list1 = Lists.newArrayList();

    public List<FormatLeaveCoolCourse> getList1() {
        return list1;
    }

    public void setList1(List<FormatLeaveCoolCourse> list) {
        this.list1 = list;
    }

    private List<FormatLeaveFruit> list2 = Lists.newArrayList();

    public List<FormatLeaveFruit> getList2() {
        return list2;
    }

    public void setList2(List<FormatLeaveFruit> list) {
        this.list2 = list;
    }
    private List<FormatLeaveMainCourse> list3 = Lists.newArrayList();

    public List<FormatLeaveMainCourse> getList3() {
        return list3;
    }

    public void setList3(List<FormatLeaveMainCourse> list) {
        this.list3= list;
    }
    private List<FormatLeaveMainFood> list4 = Lists.newArrayList();

    public List<FormatLeaveMainFood> getList4() {
        return list4;
    }

    public void setList4(List<FormatLeaveMainFood> list) {
        this.list4 = list;
    }
    private List<FormatLeaveSoup> list5 = Lists.newArrayList();

    public List<FormatLeaveSoup> getList5() {
        return list5;
    }

    public void setList5(List<FormatLeaveSoup> list) {
        this.list5 = list;
    }


}
