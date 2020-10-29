package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysIndustry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysIndustryMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysIndustry record);
    int insertSelective(SysIndustry record);
    SysIndustry selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysIndustry record);
    int updateByPrimaryKey(SysIndustry record);

    int countList();
    List<SysIndustry> getPage(@Param("page") PageQuery page);
    int countListStatus();
    List<SysIndustry> getPageStatus(@Param("page") PageQuery page);
    int countByName(@Param("name") String name, @Param("id") Integer id);
    List<SysIndustry> getList();
    List<SysIndustry> getByIdList(@Param("idList") List<Integer> idList);
}