package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionConfigBigClass;

import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 16:11
 */
public interface SupervisionConfigBigClassService {
    PageResult<SupervisionConfigBigClass> getPage (PageQuery pageQuery);
    List<SupervisionConfigBigClass> getList();
    void insert(SupervisionConfigBigClass supervisionConfigBigClass);
    void delete(int id);
    void update(SupervisionConfigBigClass supervisionConfigBigClass);
}
