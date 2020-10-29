package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysIndustry;

import java.util.List;

/**
 * @author zcc
 * @date 2019/5/18 18:41
 */
public interface SysIndustryService {
    PageResult<SysIndustry> getPage(PageQuery pageQuery);
    List<SysIndustry> getAll();
    void insert(SysIndustry sysIndustry);
    void update(SysIndustry sysIndustry);
    void delete(int id);
}
