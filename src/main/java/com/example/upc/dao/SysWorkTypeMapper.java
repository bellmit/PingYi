package com.example.upc.dao;

import com.example.upc.controller.param.SysWorkTypeParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysWorkType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysWorkTypeMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(SysWorkType record);
    int insertSelective(SysWorkType record);
    SysWorkType selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysWorkType record);
    int updateByPrimaryKey(SysWorkType record);

    int countList();
    List<SysWorkTypeParam> getPage(@Param("page") PageQuery page);
    int countByIndustryAndName(@Param("industryId") int industryId,@Param("name") String name, @Param("id") Integer id);
    List<SysWorkType> getList();
}