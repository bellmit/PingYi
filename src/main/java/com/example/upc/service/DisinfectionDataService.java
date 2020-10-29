package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysDisinfectionData;

public interface DisinfectionDataService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SysDisinfectionData sysDisinfectionData);
    void delete(int ddId);
    void update(SysDisinfectionData sysDisinfectionData);
}
