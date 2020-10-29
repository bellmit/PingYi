package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.GridMapInfo;
import com.example.upc.service.model.GridLevelDto;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/9 23:34
 */
public interface GridMapInfoService {
    PageResult<GridMapInfo> getPage(PageQuery pageQuery);
    List<GridLevelDto> gridTree();
    void insert(GridMapInfo gridMapInfo);
    void update(GridMapInfo gridMapInfo);
    void delete(int id);
}
