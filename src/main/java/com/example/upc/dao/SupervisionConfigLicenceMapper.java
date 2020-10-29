package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigLicence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionConfigLicenceMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionConfigLicence record);
    int insertSelective(SupervisionConfigLicence record);
    SupervisionConfigLicence selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionConfigLicence record);
    int updateByPrimaryKey(SupervisionConfigLicence record);

    int countList();
    List<SupervisionConfigLicence> getPage(@Param("page") PageQuery page);
    List<SupervisionConfigLicence> getByIndustry(@Param("id") Integer id);
}