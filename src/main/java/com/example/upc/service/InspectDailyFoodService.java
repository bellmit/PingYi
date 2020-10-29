package com.example.upc.service;

import com.example.upc.controller.param.InspectBookParam;
import com.example.upc.controller.param.InspectDailyClauseParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.InspectSearchParam;
import com.example.upc.dataobject.InspectDailyFood;
import com.example.upc.dataobject.SysArea;
import com.example.upc.dataobject.SysIndustry;

import java.util.List;
import java.util.Map;


/**
 * @author zcc
 * @date 2019/5/18 18:26
 */
public interface InspectDailyFoodService {
    PageResult<InspectDailyFood> getPage(PageQuery pageQuery, int industry, List<Integer> areaList, InspectSearchParam searchParam);
    InspectDailyFood getInspectDailyFood(int checkId);
    void insert(InspectDailyFood inspectDailyFood, List<InspectDailyClauseParam> inspectDailyClauseParamList);
    void update(InspectDailyFood inspectDailyFood, List<InspectDailyClauseParam> inspectDailyClauseParamList);
    void delete(int id);
    String getCheckLastDate(int objectId);
    int yearCheckNumber(int objectId);
    Map<String,Object> getStatistics(String checkDate);
}
