package com.example.upc.service;

import com.example.upc.controller.param.SysWorkTypeParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysWorkType;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 20:48
 */
public interface SysWorkTypeService {
    PageResult<SysWorkTypeParam> getPage(PageQuery pageQuery);
    void insert(SysWorkType sysWorkType);
    void update(SysWorkType sysWorkType);
    void delete(int id);
    List<SysWorkType> getAll();
}
