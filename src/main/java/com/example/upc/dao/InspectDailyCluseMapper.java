package com.example.upc.dao;

import com.example.upc.dataobject.InspectDailyCluse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectDailyCluseMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(InspectDailyCluse record);
    int insertSelective(InspectDailyCluse record);
    InspectDailyCluse selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectDailyCluse record);
    int updateByPrimaryKeyWithBLOBs(InspectDailyCluse record);
    int updateByPrimaryKey(InspectDailyCluse record);

    void deleteByDailyId(@Param("dailyId") int dailyId);
    void batchInsert(@Param("inspectDailyCluseList") List<InspectDailyCluse> inspectDailyCluseList);
}