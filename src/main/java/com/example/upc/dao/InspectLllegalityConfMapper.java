package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectLllegalityConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectLllegalityConfMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectLllegalityConf record);
    int insertSelective(InspectLllegalityConf record);
    InspectLllegalityConf selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectLllegalityConf record);
    int updateByPrimaryKey(InspectLllegalityConf record);

    int countList();
    List<InspectLllegalityConf> getPage(@Param("page") PageQuery page);
    List<InspectLllegalityConf> getList();

}