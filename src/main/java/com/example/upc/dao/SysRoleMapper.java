package com.example.upc.dao;

import com.example.upc.dataobject.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysRole record);
    int insertSelective(SysRole record);
    SysRole selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysRole record);
    int updateByPrimaryKey(SysRole record);


    List<SysRole> getAll();

    List<SysRole> getList();

    int countByName(@Param("name") String name, @Param("id") Integer id);

    List<SysRole> getByIdList(@Param("idList") List<Integer> idList);
}