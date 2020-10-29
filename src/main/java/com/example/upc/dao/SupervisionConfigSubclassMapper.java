package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigSubclass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionConfigSubclassMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionConfigSubclass record);
    int insertSelective(SupervisionConfigSubclass record);
    SupervisionConfigSubclass selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionConfigSubclass record);
    int updateByPrimaryKey(SupervisionConfigSubclass record);

    int countList();
    List<SupervisionConfigSubclass> getPage(@Param("page") PageQuery page);
}