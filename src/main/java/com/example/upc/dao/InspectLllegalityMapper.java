package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectLllegality;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectLllegalityMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectLllegality record);
    int insertSelective(InspectLllegality record);
    InspectLllegality selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectLllegality record);
    int updateByPrimaryKey(InspectLllegality record);
    int countList();
    List<InspectLllegality> getPage(@Param("page") PageQuery page);
    void batchInsert(@Param("inspectLllegalityList") List<InspectLllegality> inspectLllegalityList);
}