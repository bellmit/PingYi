package com.example.upc.dao;

import com.example.upc.controller.param.MorningCheckOutputParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCaMorningCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionCaMorningCheckMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionCaMorningCheck record);
    int insertSelective(SupervisionCaMorningCheck record);
    SupervisionCaMorningCheck selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionCaMorningCheck record);
    int updateByPrimaryKey(SupervisionCaMorningCheck record);

    int countList();
    List<SupervisionCaMorningCheck> getPage(@Param("page") PageQuery page);
    int countListByCaId(Integer id);
    List<SupervisionCaMorningCheck> getPageByCaId(@Param("page") PageQuery page,@Param("id")Integer id);
    List<MorningCheckOutputParam> output(@Param("start") String start, @Param("end") String end);
}