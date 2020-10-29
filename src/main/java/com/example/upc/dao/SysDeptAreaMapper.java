package com.example.upc.dao;

import com.example.upc.dataobject.SysDeptArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptAreaMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysDeptArea record);
    int insertSelective(SysDeptArea record);
    SysDeptArea selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysDeptArea record);
    int updateByPrimaryKey(SysDeptArea record);

    void batchInsert(@Param("deptAreaList") List<SysDeptArea> deptAreaList);
    void deleteByDeptId(@Param("deptId") int deptId);
    List<Integer> getAreaIdListByDeptId(@Param("deptId") int deptId);
    List<Integer> getAreaIdListByNotHalf(@Param("deptId") int deptId);
    List<Integer> getDeptIdListByAreaId(@Param("areaId") int areaId);
}