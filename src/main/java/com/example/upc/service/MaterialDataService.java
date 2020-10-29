package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysMaterialData;

public interface MaterialDataService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SysMaterialData sysMaterialData);
    void delete(int mdId);
    void update(SysMaterialData sysMaterialData);
}
