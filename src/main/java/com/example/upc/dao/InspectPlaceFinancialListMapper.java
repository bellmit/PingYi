package com.example.upc.dao;

import com.example.upc.dataobject.InspectPlaceFinancialList;
import org.apache.ibatis.annotations.Param;

public interface InspectPlaceFinancialListMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectPlaceFinancialList record);
    int insertSelective(InspectPlaceFinancialList record);
    InspectPlaceFinancialList selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectPlaceFinancialList record);
    int updateByPrimaryKey(InspectPlaceFinancialList record);

    InspectPlaceFinancialList getByParentId(@Param("parentId")Integer parentId);
    int countByParentId(@Param("parentId") Integer parentId, @Param("id") Integer id);
    int deleteByParentId(@Param("parentId") Integer parentId);
}