package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.InspectSearchParam;
import com.example.upc.dataobject.InspectDailyFood;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectDailyFoodMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectDailyFood record);
    int insertSelective(InspectDailyFood record);
    InspectDailyFood selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectDailyFood record);
    int updateByPrimaryKey(InspectDailyFood record);

    int countList(@Param("industry")int industry, @Param("areaList")List<Integer> areaList,@Param("search") InspectSearchParam search);
    List<InspectDailyFood> getPage(@Param("page") PageQuery page, @Param("industry")int industry, @Param("areaList")List<Integer> areaList,@Param("search") InspectSearchParam search);
    InspectDailyFood getCheckLastDate(@Param("objectId") Integer objectId);
    int yearCheckNumber(@Param("objectId")Integer objectId,@Param("year")String year);
    int countEnterpriseByAssessment(@Param("yearAssessment")String yearAssessment,@Param("industry") int industry);
    int countByEnterprise(@Param("area") int area,@Param("industry") int industry);
    int countByCheckEnterprise(@Param("area") int area,@Param("industry") int industry,@Param("checkDate") String checkDate);
    Integer countShouldCheckNumber(@Param("area") int area,@Param("industry") int industry);
    int countHaveCheckNumber(@Param("area") int area,@Param("industry") int industry,@Param("checkDate") String checkDate);
}