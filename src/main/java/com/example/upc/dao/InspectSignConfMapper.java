package com.example.upc.dao;

import com.example.upc.controller.param.InspectSignParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.InspectSignSearchParam;
import com.example.upc.dataobject.InspectSignConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectSignConfMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectSignConf record);
    int insertSelective(InspectSignConf record);
    InspectSignConf selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectSignConf record);
    int updateByPrimaryKey(InspectSignConf record);

    int countList(@Param("searchParam")InspectSignSearchParam searchParam,@Param("idList")List<Integer> idList);
    List<InspectSignParam> getPage(@Param("page") PageQuery page,@Param("searchParam")InspectSignSearchParam searchParam,@Param("idList")List<Integer> idList);
    int countByGaId(@Param("gaId") Integer gaId, @Param("id") Integer id);
}