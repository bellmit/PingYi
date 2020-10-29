package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysDept record);
    int insertSelective(SysDept record);
    SysDept selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysDept record);
    int updateByPrimaryKey(SysDept record);


    int countList();
    List<SysDept> getPage(@Param("page") PageQuery page);
    List<SysDept> getAllDept();
    void batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);
    List<SysDept> getChildDeptListByLevel(@Param("level") String level);
    List<Integer> getChildIdListByLevel(@Param("level") String level);
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);
    int countByParentId(@Param("deptId") int deptId);
    void changeLeaderId(@Param("deptId") int deptId,@Param("leaderId") int leaderId);
    List<SysDept> getDeptByType(@Param("type") int type);
}