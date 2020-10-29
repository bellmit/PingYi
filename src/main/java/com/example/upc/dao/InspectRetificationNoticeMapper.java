package com.example.upc.dao;

import com.example.upc.dataobject.InspectRetificationNotice;
import org.apache.ibatis.annotations.Param;

public interface InspectRetificationNoticeMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectRetificationNotice record);
    int insertSelective(InspectRetificationNotice record);
    InspectRetificationNotice selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectRetificationNotice record);
    int updateByPrimaryKey(InspectRetificationNotice record);

    InspectRetificationNotice getByParentId(@Param("parentId")Integer parentId);
    int countByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id);
    int deleteByParentId(@Param("parentId") Integer parentId);
}