package com.example.upc.dao;

import com.example.upc.dataobject.SupervisionEnProCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionEnProCategoryMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionEnProCategory record);
    int insertSelective(SupervisionEnProCategory record);
    SupervisionEnProCategory selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionEnProCategory record);
    int updateByPrimaryKey(SupervisionEnProCategory record);
    List<SupervisionEnProCategory> selectByParentId(@Param("id") int id);
    void deleteByParentId(@Param("parentId") int parentId);
    void batchInsert(@Param("supervisionEnProCategoryList") List<SupervisionEnProCategory> supervisionEnProCategoryList);
    void deleteByEnterpriseIds(@Param("enterpriseIds")List<Integer> enterpriseIds);
    List<SupervisionEnProCategory> getAll();
}