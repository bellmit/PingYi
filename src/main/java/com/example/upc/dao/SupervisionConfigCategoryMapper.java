package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionConfigCategoryMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionConfigCategory record);
    int insertSelective(SupervisionConfigCategory record);
    SupervisionConfigCategory selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionConfigCategory record);
    int updateByPrimaryKey(SupervisionConfigCategory record);

    int countList();
    List<SupervisionConfigCategory> getPage(@Param("page") PageQuery page);
    List<SupervisionConfigCategory> getByIndustry(@Param("id") Integer id);
}