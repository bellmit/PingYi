package com.example.upc.service;

import com.example.upc.controller.param.InspectBookParam;
import com.example.upc.controller.param.NameParam;
import com.example.upc.dataobject.InspectDailyBook;
import com.example.upc.dataobject.InspectDailyFood;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/7 1:37
 */
public interface InspectDailyBookService {
    void changeDailyBooks(int dailyFoodId, List<InspectBookParam> inspectBookParamList);
    List<InspectBookParam> getByDailyFoodId(int id);
    void insert(InspectDailyBook inspectDailyBook);
    void update(InspectDailyBook inspectDailyBook);
    void delete(int id);
    void deleteByDailyId(int id);
    List<NameParam> getCheckBookNameList(int dailyFoodId, String typeUrl);
}
