package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAreaMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysArea record);
    int insertSelective(SysArea record);
    SysArea selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysArea record);
    int updateByPrimaryKey(SysArea record);

    int countList();
    List<SysArea> getPage(@Param("page") PageQuery page);
    List<SysArea> getAllArea();
    List<SysArea> getAllAreaEx();
    void batchUpdateLevel(@Param("sysAreaList") List<SysArea> sysAreaList);
    List<SysArea> getChildAreaListByLevel(@Param("level") String level);
    List<Integer> getChildIdListByLevel(@Param("level") String level);
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);
    int countByParentId(@Param("areaId") int areaId);
    List<SysArea> getByIdList(@Param("idList") List<Integer> idList);
    List<SysArea> getAllAreaPa();
}