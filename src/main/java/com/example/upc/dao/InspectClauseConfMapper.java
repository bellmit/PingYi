package com.example.upc.dao;

import com.example.upc.controller.param.InspectClauseConfParam;
import com.example.upc.controller.param.InspectDailyClauseParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectClauseConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectClauseConfMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectClauseConf record);
    int insertSelective(InspectClauseConf record);
    InspectClauseConf selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectClauseConf record);
    int updateByPrimaryKey(InspectClauseConf record);

    int countList();
    List<InspectClauseConfParam> getPage(@Param("page") PageQuery page);
    List<InspectDailyClauseParam> getList(@Param("id") int id, @Param("industryId") int industryId);
    List<InspectDailyClauseParam> getEmptyList(@Param("industryId") int industryId);

    int countByName(@Param("clauseName") String name,@Param("largeClass") int largeClass, @Param("id") Integer id);
}