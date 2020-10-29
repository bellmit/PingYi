package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysSupervisor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysSupervisorMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysSupervisor record);
    int insertSelective(SysSupervisor record);
    SysSupervisor selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysSupervisor record);
    int updateByPrimaryKey(SysSupervisor record);
    int countList();
    List<SysSupervisor> getPage(@Param("page") PageQuery page);
    SysSupervisor selectAllByTelephone(String telephone);
}