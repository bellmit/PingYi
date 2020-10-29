package com.example.upc.dao;

import com.example.upc.controller.param.InspectBookParam;
import com.example.upc.controller.param.NameParam;
import com.example.upc.dataobject.InspectDailyBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InspectDailyBookMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(InspectDailyBook record);
    int insertSelective(InspectDailyBook record);
    InspectDailyBook selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(InspectDailyBook record);
    int updateByPrimaryKey(InspectDailyBook record);

    int deleteByDailyFoodId(@Param("dailyFoodId")int dailyFoodId);
    void batchInsert(@Param("dailyBookList") List<InspectDailyBook> dailyBookList);
    List<InspectBookParam> getByDailyFoodId(@Param("dailyFoodId")int dailyFoodId);
    void changeBookRemark(@Param("remark") String remark,@Param("id") int id);
    List<NameParam>  getNameListByList(@Param("dailyFoodId") int dailyFoodId);
    List<NameParam>  getNameListByForce(@Param("dailyFoodId") int dailyFoodId);
}