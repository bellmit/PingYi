package com.example.upc.controller.param;

import com.example.upc.dataobject.SupervisionEnFoodPro;
import com.example.upc.dataobject.SupervisionEnProCategory;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/1 23:15
 */
public class EnFoodProduceParam extends SupervisionEnFoodPro {
    private List<SupervisionEnProCategory> list = Lists.newArrayList();

    public List<SupervisionEnProCategory> getList() {
        return list;
    }

    public void setList(List<SupervisionEnProCategory> list) {
        this.list = list;
    }
}
