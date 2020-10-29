package com.example.upc.service;

import com.example.upc.controller.param.InspectDailyClauseParam;

import java.util.List;

/**
 * @author zcc
 * @date 2019/5/18 18:54
 */
public interface InspectDailyClauseService {
    List<InspectDailyClauseParam> getList(int id,int industryId);
    List<InspectDailyClauseParam> getEmptyList(int industryId);
    void changeDailyClauseList(List<InspectDailyClauseParam> inspectDailyClauseParamList,int dailyId);

}
