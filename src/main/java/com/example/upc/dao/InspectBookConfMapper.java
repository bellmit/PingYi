package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectBookConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectBookConfMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectBookConf record);
    int insertSelective(InspectBookConf record);
    InspectBookConf selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectBookConf record);
    int updateByPrimaryKey(InspectBookConf record);

    int countList();
    List<InspectBookConf> getPage(@Param("page") PageQuery page);
}