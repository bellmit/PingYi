package com.example.upc.service;

import com.example.upc.controller.param.GridGrid1;
import com.example.upc.dataobject.GridGrid;

import java.util.List;

/**
 * @author zcc
 * @date 2019/8/13 11:09
 */
public interface GridGridService {
    int insertSelective(GridGrid record);
    List<GridGrid1> getAll();
    List<GridGrid1> getTop();
    List<GridGrid1> getByParentId(int id);
    int getParentId(int parentId);
    int updateByAreaId(GridGrid record);

}
