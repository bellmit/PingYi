package com.example.upc.dao;

import com.example.upc.dataobject.InspectThingList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectThingListMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectThingList record);
    int insertSelective(InspectThingList record);
    InspectThingList selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectThingList record);
    int updateByPrimaryKey(InspectThingList record);

    List<InspectThingList> getByParentId(@Param("parentId") Integer parentId);
    int deleteByParentId(@Param("parentId") Integer parentId);
    void batchInsert(@Param("inspectThingList") List<InspectThingList> inspectThingList);
}