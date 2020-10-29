package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.FoodSupervision;

public interface FoodSupervisionService {
    PageResult getPage (PageQuery pageQuery);
    void insert(FoodSupervision sysFoodProduce);
    void delete(int fpId);
    void update(FoodSupervision sysFoodProduce);
}
