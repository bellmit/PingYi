package com.example.upc.dao;

import com.example.upc.controller.param.GridGrid1;
import com.example.upc.dataobject.GridGrid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GridGridMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(GridGrid record);
    int insertSelective(GridGrid record);
    GridGrid selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(GridGrid record);
    int updateByPrimaryKey(GridGrid record);

    List<GridGrid1> getAll();
    List<GridGrid1> getTop();
    List<GridGrid1> getByParentId(@Param("id") Integer id);
    int getParentId(@Param("id") int id);
    int updateByAreaId(GridGrid record);
}