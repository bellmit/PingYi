package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCredit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionCreditMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionCredit record);
    int insertSelective(SupervisionCredit record);
    SupervisionCredit selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionCredit record);
    int updateByPrimaryKey(SupervisionCredit record);

    int countList();
    List<SupervisionCredit> getPage(@Param("page") PageQuery page);
}