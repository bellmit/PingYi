package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.searchParam.AccompanySearchParam;
import com.example.upc.dao.AccompanyRecordMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.AccompanyRecordService;
import com.example.upc.util.operateExcel.AccompanyMeal;
import com.example.upc.util.operateExcel.WasteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccompanyRecordServiceImpl implements AccompanyRecordService {
    @Autowired
    AccompanyRecordMapper accompanyRecordMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;

    @Override
    public void insert(AccompanyRecord accompanyRecord, SysUser sysUser) {
        accompanyRecord.setOperator(sysUser.getLoginName());
        accompanyRecord.setOperatorIp("127.0.0.1");
        accompanyRecord.setOperatorTime(new Date());
        accompanyRecord.setEnterpriseId(sysUser.getInfoId());
        accompanyRecordMapper.insert(accompanyRecord);
        return;
    }

    @Override
    public void update(AccompanyRecord accompanyRecord, SysUser sysUser) {
        AccompanyRecord accompanyRecord1 =accompanyRecordMapper.selectByInfoIdAndId(sysUser.getInfoId(),accompanyRecord.getId());
        if(accompanyRecord1==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        accompanyRecord.setOperator(sysUser.getLoginName());
        accompanyRecord.setEnterpriseId(sysUser.getInfoId());
        accompanyRecord.setOperatorTime(new Date());
        accompanyRecord.setOperatorIp("127.0.0.1");
        accompanyRecordMapper.updateByPrimaryKey(accompanyRecord);
        return;
    }

    @Override
    public void delete(AccompanyRecord accompanyRecord, SysUser sysUser) {
        accompanyRecordMapper.deleteByPrimaryKey(accompanyRecord.getId());
        return;
    }

    @Override
    public Object getAccompanyRecord( SysUser sysUser) {
        List<AccompanyRecord> accList = accompanyRecordMapper.getAllRecord(sysUser.getInfoId());
        return accList;
    }

    @Override
    public List<FormatDisinfection> getAccompanyRecordByDate(Date startDate, SysUser sysUser) {
        Date endDate = new Date();
        endDate.setTime(startDate.getTime()+86399999);
        List<FormatDisinfection> searchList = accompanyRecordMapper.getAccompanyRecordByDate(sysUser.getInfoId(), startDate, endDate);
        return searchList;
    }

    @Override
    public Object standingAccompanyRecord(Integer id, SysUser sysUser) throws Exception {
        AccompanyRecord accompanyRecord =accompanyRecordMapper.selectByPrimaryKey(id);
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> picmap = new HashMap<>();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data1.put("${date}",simpleDateFormat.format(accompanyRecord.getMealDate()));
        //获取企业名字
        SupervisionEnterprise supervisionEnterprise=supervisionEnterpriseMapper.selectByPrimaryKey(accompanyRecord.getEnterpriseId());
        data.put("${name}",supervisionEnterprise.getEnterpriseName());
        data.put("${person}",accompanyRecord.getMealAccompanys());
        data.put("${meal}",accompanyRecord.getMealTime());
        data.put("${hygiene}",accompanyRecord.getCanteenHealth());
        data.put("${quality}",accompanyRecord.getFoodQuality());
        data.put("${account}",accompanyRecord.getMealNumber());
        data.put("${attitude}",accompanyRecord.getStaffAttitude());
        data.put("${standard}",accompanyRecord.getStaffSpecifications());
        data.put("${satisfaction}",accompanyRecord.getTotalSatisfaction());


        picmap.put("${pic1}",accompanyRecord.getDiningWindow());
        picmap.put("${pic2}",accompanyRecord.getMealRecord());
        picmap.put("${pic3}",accompanyRecord.getDiningEnvironment());

        AccompanyMeal.getWord(data1,data,picmap,accompanyRecord.getEnterpriseId());
        return null;
    }
}
