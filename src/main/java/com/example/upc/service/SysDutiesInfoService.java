package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysDutiesInfo;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/9 19:16
 */
public interface SysDutiesInfoService {
    PageResult<SysDutiesInfo> getPage(PageQuery pageQuery);
    void insert(SysDutiesInfo sysDutiesInfo);
    void update(SysDutiesInfo sysDutiesInfo);
    void delete(int id);
    List<SysDutiesInfo> getList();
}
