package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.QuickSpotCheckResultType;

public interface QuickSpotCheckResultTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(QuickSpotCheckResultType quickSpotCheckResultType);
    void delete(int id);
    void update(QuickSpotCheckResultType quickSpotCheckResultType);
}
