package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigBigClass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionConfigBigClassMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionConfigBigClass record);
    int insertSelective(SupervisionConfigBigClass record);
    SupervisionConfigBigClass selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionConfigBigClass record);
    int updateByPrimaryKey(SupervisionConfigBigClass record);

    int countList();
    List<SupervisionConfigBigClass> getPage(@Param("page") PageQuery page);
    List<SupervisionConfigBigClass> getList();
}