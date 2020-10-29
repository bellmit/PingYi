package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.TypeSearchParam;
import com.example.upc.dataobject.FormatOriginFood;
import com.example.upc.dataobject.SysUser;

public interface FormatOriginFoodService {
    PageResult getPage (PageQuery pageQuery, TypeSearchParam typeSearchParam, SysUser sysUser);
    void insert(FormatOriginFood formatOriginFood,SysUser sysUser);
    void delete(int foId);
    void update(FormatOriginFood formatOriginFood,SysUser sysUser);
}
