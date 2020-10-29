package com.example.upc.service.impl;

import com.example.upc.controller.param.ComplaintRecordListParam;
import com.example.upc.controller.param.ComplaintRecordStatisticsParam;
import com.example.upc.controller.searchParam.ComplaintRecordSearchParam;
import com.example.upc.dao.ComplaintRecordMapper;
import com.example.upc.service.ComplaintRecordStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintRecordStatisticsServiceImpl implements ComplaintRecordStatisticsService {
    @Autowired
    private ComplaintRecordMapper complaintRecordMapper;

    @Override
    public ComplaintRecordStatisticsParam getListTypeNumberByDate (ComplaintRecordSearchParam complaintRecordSearchParam)
    {
        ComplaintRecordStatisticsParam complaintRecordStatisticsParam = new ComplaintRecordStatisticsParam();

        Integer total = complaintRecordMapper.countAllTotal(complaintRecordSearchParam);
        complaintRecordStatisticsParam.setTotal(total);

        List<ComplaintRecordListParam> list1 = complaintRecordMapper.getListTypeNumberByDate(complaintRecordSearchParam);
        complaintRecordStatisticsParam.setList1(list1);

        List<ComplaintRecordListParam> list2 = complaintRecordMapper.getListTypeNumberByDateStep(complaintRecordSearchParam);
        complaintRecordStatisticsParam.setList2(list2);

        List<ComplaintRecordListParam> list3 = complaintRecordMapper.getListTypeNumberByDateDept(complaintRecordSearchParam);
        complaintRecordStatisticsParam.setList3(list3);

        List<ComplaintRecordListParam> list4 = complaintRecordMapper.getListTypeNumberAll();
//        ComplaintRecordListParam complaintRecordListParam3 = new ComplaintRecordListParam();
//        complaintRecordListParam3.setType("总计");
//        complaintRecordListParam3.setNumber(complaintRecordMapper.countTotalAll());
//        list4.add(complaintRecordListParam3);
        complaintRecordStatisticsParam.setList4(list4);

        return complaintRecordStatisticsParam;
    }

}
