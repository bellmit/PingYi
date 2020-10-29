package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SpotCheckCheckType;

public interface SpotCheckCheckTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SpotCheckCheckType spotCheckCheckType);
    void delete(int fpId);
    void update(SpotCheckCheckType spotCheckCheckType);
}
