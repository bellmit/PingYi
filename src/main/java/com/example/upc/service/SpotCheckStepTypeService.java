package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SpotCheckStepType;

public interface SpotCheckStepTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SpotCheckStepType spotCheckStepType);
    void delete(int fpId);
    void update(SpotCheckStepType spotCheckStepType);
}
