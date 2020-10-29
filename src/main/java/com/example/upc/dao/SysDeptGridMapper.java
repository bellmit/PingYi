package com.example.upc.dao;

import com.example.upc.dataobject.SysDeptGrid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptGridMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysDeptGrid record);
    int insertSelective(SysDeptGrid record);
    SysDeptGrid selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysDeptGrid record);
    int updateByPrimaryKey(SysDeptGrid record);

    void batchInsert(@Param("deptGridList") List<SysDeptGrid> deptGridList);
    void deleteByDeptId(@Param("deptId") int deptId);
    List<Integer> getGridIdListByDeptId(@Param("deptId") int deptId);
}