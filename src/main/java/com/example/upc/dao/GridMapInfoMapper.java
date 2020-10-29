package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.GridMapInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GridMapInfoMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(GridMapInfo record);
    int insertSelective(GridMapInfo record);
    GridMapInfo selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(GridMapInfo record);
    int updateByPrimaryKey(GridMapInfo record);

    int countList();
    List<GridMapInfo> getPage(@Param("page") PageQuery page);
    List<GridMapInfo> getAllGrid();
    void batchUpdateLevel(@Param("gridMapList") List<GridMapInfo> gridMapList);
    List<GridMapInfo> getChildGridListByLevel(@Param("level") String level);
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);
    int countByParentId(@Param("gridId") int gridId);
    List<GridMapInfo> getByIdList(@Param("idList") List<Integer> idList);
}