package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SupervisionConfigCategory;

import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 16:15
 */
public interface SupervisionConfigCategoryService {
    PageResult<SupervisionConfigCategory> getPage (PageQuery pageQuery);
    void insert(SupervisionConfigCategory supervisionConfigCategory);
    void delete(int id);
    void update(SupervisionConfigCategory supervisionConfigCategory);
    List<SupervisionConfigCategory> getByPermiss(int id);
}
