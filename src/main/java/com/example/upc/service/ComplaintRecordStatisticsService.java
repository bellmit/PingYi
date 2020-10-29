package com.example.upc.service;

import com.example.upc.controller.param.ComplaintRecordStatisticsParam;
import com.example.upc.controller.searchParam.ComplaintRecordSearchParam;


public interface ComplaintRecordStatisticsService {
    ComplaintRecordStatisticsParam getListTypeNumberByDate (ComplaintRecordSearchParam complaintRecordSearchParam);
}
