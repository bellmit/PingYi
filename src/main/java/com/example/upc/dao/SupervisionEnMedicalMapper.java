package com.example.upc.dao;

import com.example.upc.dataobject.SupervisionEnMedical;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionEnMedicalMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionEnMedical record);
    int insertSelective(SupervisionEnMedical record);
    SupervisionEnMedical selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionEnMedical record);
    int updateByPrimaryKey(SupervisionEnMedical record);
    SupervisionEnMedical selectByEnterpriseId(@Param("id") int id);
    void deleteByEnterpriseId(@Param("enterpriseId") int enterpriseId);
    void batchInsert(@Param("medicalList") List<SupervisionEnMedical> medicalList);
    void deleteByEnterpriseIds(@Param("enterpriseIds")List<Integer> enterpriseIds);
}