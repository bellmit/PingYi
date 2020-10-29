package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysDutiesInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDutiesInfoMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysDutiesInfo record);
    int insertSelective(SysDutiesInfo record);
    SysDutiesInfo selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysDutiesInfo record);
    int updateByPrimaryKey(SysDutiesInfo record);

    int countList();
    List<SysDutiesInfo> getPage(@Param("page") PageQuery page);
    int countByName(@Param("name") String name, @Param("id") Integer id);
    List<SysDutiesInfo> getList();
}