package com.example.upc.dao;

import com.example.upc.dataobject.InspectBookRecord;
import org.apache.ibatis.annotations.Param;

public interface InspectBookRecordMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectBookRecord record);
    int insertSelective(InspectBookRecord record);
    InspectBookRecord selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectBookRecord record);
    int updateByPrimaryKey(InspectBookRecord record);

    InspectBookRecord getByParentId(@Param("parentId")Integer parentId);
    int countByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id);
    int deleteByParentId(@Param("parentId") Integer parentId);
}