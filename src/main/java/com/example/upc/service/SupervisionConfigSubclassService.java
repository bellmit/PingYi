package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionConfigSubclass;

/**
 * @author zcc
 * @date 2019/6/26 16:39
 */
public interface SupervisionConfigSubclassService {
    PageResult<SupervisionConfigSubclass> getPage (PageQuery pageQuery);
    void insert(SupervisionConfigSubclass supervisionConfigSubclass);
    void delete(int id);
    void update(SupervisionConfigSubclass supervisionConfigSubclass);
}
