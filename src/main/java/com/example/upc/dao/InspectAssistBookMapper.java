package com.example.upc.dao;

import com.example.upc.dataobject.InspectAssistBook;
import org.apache.ibatis.annotations.Param;

public interface InspectAssistBookMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectAssistBook record);
    int insertSelective(InspectAssistBook record);
    InspectAssistBook selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectAssistBook record);
    int updateByPrimaryKey(InspectAssistBook record);

    InspectAssistBook getByParentId(@Param("parentId")Integer parentId);
    int countByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id);
    int deleteByParentId(@Param("parentId") Integer parentId);
}