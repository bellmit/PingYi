package com.example.upc.service;

import com.example.upc.controller.param.FormatAdditiveParam;
import com.example.upc.controller.param.FormatAdditiveSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.AdditiveSearchParam;
import com.example.upc.dataobject.FormatAdditive;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;

public interface FormatAdditiveService {
    PageResult<FormatAdditiveSupParam> getPage (PageQuery pageQuery, AdditiveSearchParam additiveSearchParam);
    PageResult<FormatAdditive> getPageEnterprise (PageQuery pageQuery, Integer id, AdditiveSearchParam additiveSearchParam);
    PageResult<FormatAdditiveSupParam> getPageAdmin (PageQuery pageQuery, AdditiveSearchParam additiveSearchParam);
    void insert(String json, SysUser sysUser);
    void update(String json,SysUser sysUser);
    void delete(int fpId);
    FormatAdditiveParam getById(int id);
    void updateRecord(int id, String publicity);
    void fail();
    SupervisionGa getGa(int id);
}
