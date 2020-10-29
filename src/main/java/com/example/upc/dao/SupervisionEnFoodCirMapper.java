package com.example.upc.dao;

import com.example.upc.dataobject.SupervisionEnFoodCir;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionEnFoodCirMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionEnFoodCir record);
    int insertSelective(SupervisionEnFoodCir record);
    SupervisionEnFoodCir selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionEnFoodCir record);
    int updateByPrimaryKey(SupervisionEnFoodCir record);
    SupervisionEnFoodCir selectByEnterpriseId(@Param("id") int id);
    void deleteByEnterpriseId(@Param("enterpriseId") int enterpriseId);
    void batchInsert(@Param("foodCirList") List<SupervisionEnFoodCir> foodCirList);
    void deleteByEnterpriseIds(@Param("enterpriseIds")List<Integer> enterpriseIds);
}