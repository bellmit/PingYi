package com.example.upc.dao;

import com.example.upc.dataobject.InspectForceDecision;
import org.apache.ibatis.annotations.Param;

public interface InspectForceDecisionMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectForceDecision record);
    int insertSelective(InspectForceDecision record);
    InspectForceDecision selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectForceDecision record);
    int updateByPrimaryKey(InspectForceDecision record);

    InspectForceDecision getByParentId(@Param("parentId")Integer parentId);
    int countByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id);
    int deleteByParentId(@Param("parentId") Integer parentId);
}