package com.example.upc.dao;

import com.example.upc.dataobject.SysDeptIndustry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptIndustryMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysDeptIndustry record);
    int insertSelective(SysDeptIndustry record);
    SysDeptIndustry selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysDeptIndustry record);
    int updateByPrimaryKey(SysDeptIndustry record);

    void batchInsert(@Param("deptIndustryList") List<SysDeptIndustry> deptIndustryList);
    void deleteByDeptId(@Param("deptId") int deptId);
    List<Integer> getIndustryIdListByDeptId(@Param("deptId") int deptId);
}