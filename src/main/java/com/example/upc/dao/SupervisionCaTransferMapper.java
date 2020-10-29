package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCaTransfer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionCaTransferMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionCaTransfer record);
    int insertSelective(SupervisionCaTransfer record);
    SupervisionCaTransfer selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionCaTransfer record);
    int updateByPrimaryKey(SupervisionCaTransfer record);
    int countList();
    List<SupervisionCaTransfer> getPage(@Param("page") PageQuery page);
    int countListByCaId(Integer id);
    List<SupervisionCaTransfer> getPageByCaId(@Param("page") PageQuery page,@Param("id")Integer id);
}