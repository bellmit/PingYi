package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigProduce;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionConfigProduceMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionConfigProduce record);
    int insertSelective(SupervisionConfigProduce record);
    SupervisionConfigProduce selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionConfigProduce record);
    int updateByPrimaryKey(SupervisionConfigProduce record);

    int countList();
    List<SupervisionConfigProduce> getPage(@Param("page") PageQuery page);
}