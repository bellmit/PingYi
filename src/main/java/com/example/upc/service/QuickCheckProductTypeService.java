package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.QuickCheckProductType;
import com.example.upc.dataobject.SpotCheckProductType;

public interface QuickCheckProductTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(QuickCheckProductType quickCheckProductType);
    void delete(int id);
    void update(QuickCheckProductType quickCheckProductType);
}
