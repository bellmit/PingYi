package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SpotCheckFoodType;

public interface SpotCheckFoodTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SpotCheckFoodType spotCheckFoodType);
    void delete(int fpId);
    void update(SpotCheckFoodType spotCheckFoodType);
}
