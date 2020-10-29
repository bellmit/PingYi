package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCreditRewards;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionCreditRewardsMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionCreditRewards record);
    int insertSelective(SupervisionCreditRewards record);
    SupervisionCreditRewards selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionCreditRewards record);
    int updateByPrimaryKey(SupervisionCreditRewards record);
    int countList();
    List<SupervisionCreditRewards> getPage(@Param("page") PageQuery page);
}
