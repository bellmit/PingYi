package com.example.upc.dao;

import com.example.upc.dataobject.InspectArriveEvidence;
import org.apache.ibatis.annotations.Param;

public interface InspectArriveEvidenceMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectArriveEvidence record);
    int insertSelective(InspectArriveEvidence record);
    InspectArriveEvidence selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectArriveEvidence record);
    int updateByPrimaryKey(InspectArriveEvidence record);

    InspectArriveEvidence getByParentId(@Param("parentId")Integer parentId);
    int countByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id);
    int deleteByParentId(@Param("parentId") Integer parentId);
}