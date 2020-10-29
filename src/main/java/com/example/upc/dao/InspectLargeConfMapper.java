package com.example.upc.dao;

import com.example.upc.controller.param.InspectLargeConfParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectLargeConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectLargeConfMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(InspectLargeConf record);
    int insertSelective(InspectLargeConf record);
    InspectLargeConf selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectLargeConf record);
    int updateByPrimaryKey(InspectLargeConf record);
    List<InspectLargeConf> getAll();
    int countList();
    List<InspectLargeConfParam> getPage(@Param("page") PageQuery page);
    int countByName(@Param("name") String name,@Param("industry") int industry);
}