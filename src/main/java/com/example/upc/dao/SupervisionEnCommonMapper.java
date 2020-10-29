package com.example.upc.dao;

import com.example.upc.dataobject.SupervisionEnCommon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionEnCommonMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionEnCommon record);
    int insertSelective(SupervisionEnCommon record);
    SupervisionEnCommon selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionEnCommon record);
    int updateByPrimaryKey(SupervisionEnCommon record);

    SupervisionEnCommon selectByEnterpriseId(@Param("id") int id);
    void deleteByEnterpriseId(@Param("enterpriseId") int enterpriseId);
    void batchInsert(@Param("commonList") List<SupervisionEnCommon> commonList);
    void deleteByEnterpriseIds(@Param("enterpriseIds")List<Integer> enterpriseIds);
}