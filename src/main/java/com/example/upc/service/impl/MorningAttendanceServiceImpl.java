package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.*;
import com.example.upc.controller.param.FormatLeaveMiniParam;
import com.example.upc.controller.param.MorningAttendenceParam;
import com.example.upc.controller.searchParam.MorningAttendanceSearchParam;
import com.example.upc.dao.MorningAttendanceInfoMapper;
import com.example.upc.dao.MorningAttendanceMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SysMaterialDataMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.MorningAttendanceService;
import com.example.upc.util.operateExcel.WasteExcel;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MorningAttendanceServiceImpl implements MorningAttendanceService {
    @Autowired
    MorningAttendanceMapper morningAttendanceMapper;
    @Autowired
    MorningAttendanceInfoMapper morningAttendanceInfoMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public void insert(String json, SysUser sysUser) {
        MorningAttendenceParam morningAttendenceParam = JSONObject.parseObject(json, MorningAttendenceParam.class);
        MorningAttendance morningAttendance = new MorningAttendance();
        BeanUtils.copyProperties(morningAttendenceParam, morningAttendance);

        ValidationResult result = validator.validate(morningAttendance);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无此企业信息");
        }

        //morningAttendance.setCheckTime(morningAttendenceParam.getCheckTime());
        morningAttendance.setEnterpriseId(sysUser.getInfoId());
        // morningAttendance.setRecorderName(morningAttendenceParam.getRecorderName());
        morningAttendance.setOperator(sysUser.getLoginName());
        morningAttendance.setOperatorIp("127.0.0.1");
        morningAttendance.setOperatorTime(new Date());
        morningAttendanceMapper.insertSelective(morningAttendance);
        if (morningAttendance.getId() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "插入失败");
        }
        List<MorningAttendanceInfo> morningAttendanceInfoList = morningAttendenceParam.getMorningAttendanceInfosList();
        if (morningAttendanceInfoList.size() > 0) {
            for (MorningAttendanceInfo morningAttendanceInfo : morningAttendanceInfoList) {
                MorningAttendanceInfo morningAttendanceInfos = new MorningAttendanceInfo();
                morningAttendanceInfos.setAttendanceId(morningAttendance.getId());
                morningAttendanceInfos.setAttendanceSituation(morningAttendanceInfo.getAttendanceSituation());
                morningAttendanceInfos.setBodyHealth(morningAttendanceInfo.getBodyHealth());
                morningAttendanceInfos.setEmployeeName(morningAttendanceInfo.getEmployeeName());
                morningAttendanceInfos.setOperator("zcc");
                morningAttendanceInfos.setOperatorIp("127.0.0.1");
                morningAttendanceInfos.setOperatorTime(new Date());
                morningAttendanceInfoMapper.insert(morningAttendanceInfos);
            }
        }
        return;
    }

    @Override
    public void update(MorningAttendenceParam morningAttendenceParam, SysUser sysUser) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无此企业信息");
        }
        MorningAttendance morningAttendance = new MorningAttendance();
        morningAttendance.setId(morningAttendenceParam.getAttendanceId());
        morningAttendance.setCheckTime(morningAttendenceParam.getCheckTime());
        morningAttendance.setEnterpriseId(sysUser.getInfoId());
        morningAttendance.setRecorderName(morningAttendenceParam.getRecorderName());
        morningAttendance.setOperator(sysUser.getLoginName());
        morningAttendance.setOperatorIp("127.0.0.1");
        morningAttendance.setOperatorTime(new Date());
        morningAttendanceMapper.updateByPrimaryKey(morningAttendance);
        if (morningAttendance.getId() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "插入失败");
        }

        List<MorningAttendanceInfo> morningAttendanceInfoList = morningAttendenceParam.getMorningAttendanceInfosList();
        //删除后插入
        morningAttendanceInfoMapper.deleteByAttendanceId(morningAttendance.getId());
        if (morningAttendanceInfoList.size() > 0) {
            for (MorningAttendanceInfo morningAttendanceInfo : morningAttendanceInfoList) {
                MorningAttendanceInfo morningAttendanceInfos = new MorningAttendanceInfo();
                morningAttendanceInfos.setAttendanceId(morningAttendance.getId());
                morningAttendanceInfos.setAttendanceSituation(morningAttendanceInfo.getAttendanceSituation());
                morningAttendanceInfos.setBodyHealth(morningAttendanceInfo.getBodyHealth());
                morningAttendanceInfos.setEmployeeName(morningAttendanceInfo.getEmployeeName());
                morningAttendanceInfos.setOperatorTime(new Date());
                morningAttendanceInfos.setOperator("Zcc");
                morningAttendanceInfos.setOperatorIp("127.0.0.1");
                morningAttendanceInfoMapper.insert(morningAttendanceInfos);
            }
        }
        return;
    }

    @Override
    public void delete(int id, SysUser sysUser) {
        MorningAttendance morningAttendance = morningAttendanceMapper.selectByPrimaryKey(id);
        if (morningAttendance == null) {
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        morningAttendanceMapper.deleteByPrimaryKey(id);
        morningAttendanceInfoMapper.deleteByAttendanceId(id);
    }

    @Override
    public CommonReturnType getMorningAttendance(SysUser sysUser) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无此企业信息");
        }
        List<MorningAttendance> morningAttendancesList = morningAttendanceMapper.getByEnterpriseMapper(sysUser.getInfoId());
        List<MorningAttendenceParam> morningAttendenceParamList = Lists.newArrayList();
        if (morningAttendancesList.size() > 0) {
            // Map<String, Object> morningMap = new LinkedHashMap<>();
            for (MorningAttendance morningAttendance : morningAttendancesList) {
                MorningAttendenceParam morningAttendenceParam = new MorningAttendenceParam();
                morningAttendenceParam.setCheckTime(morningAttendance.getCheckTime());
                morningAttendenceParam.setRecorderName(morningAttendance.getRecorderName());
                morningAttendenceParam.setAttendanceId(morningAttendance.getId());
                morningAttendenceParam.setEnterpriseId(morningAttendance.getEnterpriseId());
                List<MorningAttendanceInfo> morningAttendanceInfoList = morningAttendanceInfoMapper.selectByAttendance(morningAttendance.getId());
                morningAttendenceParam.setMorningAttendanceInfosList(morningAttendanceInfoList);
                morningAttendenceParamList.add(morningAttendenceParam);
                //  morningInfoMap.put("morningAttendanceInfo",morningAttendanceInfoList);
                //  morningMap.put("",morningInfoMap);
            }
            return CommonReturnType.create(morningAttendenceParamList);
        }
        return null;
    }

    @Override
    public CommonReturnType getMorningAttendanceByDate(Date startDate, SysUser sysUser) {
        Date endDate = new Date();
        endDate.setTime(startDate.getTime() + 86399999);
        List<MorningAttendance> morningAttendancesList = morningAttendanceMapper.getMorningAttendanceByDate(sysUser.getInfoId(), startDate, endDate);
        //List<MorningAttendance> morningAttendancesList = morningAttendanceMapper.getMorningAttendanceByDate(sysUser.getInfoId());
        List<MorningAttendenceParam> morningAttendenceParamList = Lists.newArrayList();
        if (morningAttendancesList.size() > 0) {
            for (MorningAttendance morningAttendance : morningAttendancesList) {
                MorningAttendenceParam morningAttendenceParam = new MorningAttendenceParam();
                morningAttendenceParam.setCheckTime(morningAttendance.getCheckTime());
                morningAttendenceParam.setRecorderName(morningAttendance.getRecorderName());
                morningAttendenceParam.setAttendanceId(morningAttendance.getId());
                morningAttendenceParam.setEnterpriseId(morningAttendance.getEnterpriseId());
                List<MorningAttendanceInfo> morningAttendanceInfoList = morningAttendanceInfoMapper.selectByAttendance(morningAttendance.getId());
                morningAttendenceParam.setMorningAttendanceInfosList(morningAttendanceInfoList);
                morningAttendenceParamList.add(morningAttendenceParam);
            }
            return CommonReturnType.create(morningAttendenceParamList);
        }
        return null;
    }

    @Override
    public Object getMorningAttendanceExcelByDate(MorningAttendanceSearchParam morningAttendanceSearchParam, SysUser sysUser) throws IOException {
        List<MorningAttendance> morningAttendancesList = morningAttendanceMapper.getMorningAttendanceByDate(sysUser.getInfoId(), morningAttendanceSearchParam.getStartDate(), morningAttendanceSearchParam.getEndDate());

        List<MorningAttendenceParam> morningAttendenceParamList = Lists.newArrayList();
        if (morningAttendancesList.size() > 0) {
            for (MorningAttendance morningAttendance : morningAttendancesList) {
                MorningAttendenceParam morningAttendenceParam = new MorningAttendenceParam();
                morningAttendenceParam.setCheckTime(morningAttendance.getCheckTime());
                morningAttendenceParam.setRecorderName(morningAttendance.getRecorderName());
                morningAttendenceParam.setAttendanceId(morningAttendance.getId());
                morningAttendenceParam.setEnterpriseId(morningAttendance.getEnterpriseId());
                List<MorningAttendanceInfo> morningAttendanceInfoList = morningAttendanceInfoMapper.selectByAttendance(morningAttendance.getId());
                morningAttendenceParam.setMorningAttendanceInfosList(morningAttendanceInfoList);
                morningAttendenceParamList.add(morningAttendenceParam);
            }
        }
        List<String[]> data = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (MorningAttendenceParam item : morningAttendenceParamList
        ) {
            for(MorningAttendanceInfo morningAttendanceInfo:item.getMorningAttendanceInfosList()){
            data.add(new String[]{
                    dateFormat.format(item.getCheckTime()), morningAttendanceInfo.getEmployeeName(), morningAttendanceInfo.getBodyHealth(), morningAttendanceInfo.getAttendanceSituation(),item.getRecorderName()
            });
            }
        }
        String fileName = "人员晨检及出勤";
        String path = WasteExcel.getXLsx(data, "/template/【导出】人员晨检及出勤模板.xlsx", fileName, sysUser.getInfoId());
        //下载
        //UploadController.downloadStandingBook(response, fileName,path);

        return path;

    }
}