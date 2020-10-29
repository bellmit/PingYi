package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SpotCheckDateType;

public interface SpotCheckDateTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SpotCheckDateType spotCheckDateType);
    void delete(int fpId);
    void update(SpotCheckDateType spotCheckDateType);
}
