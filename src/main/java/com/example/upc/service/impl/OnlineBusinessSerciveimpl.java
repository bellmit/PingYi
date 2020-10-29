package com.example.upc.service.impl;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.OnlineBusinessParm;
import com.example.upc.controller.searchParam.OnlineBusinessSearchParam;
import com.example.upc.dao.OnlineBusinessMapper;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.OnlineBusiness;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.util.miniProgram.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.upc.service.OnlineBusinessService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OnlineBusinessSerciveimpl implements OnlineBusinessService {
    @Autowired
    OnlineBusinessMapper onlineBusinessMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private ValidatorImpl validator;
    @Override
    public OnlineBusiness getMessageByEnterpriseId(OnlineBusinessSearchParam onlineBusinessSearchParam) {
        OnlineBusiness onlineBusiness = onlineBusinessMapper.getMessageByEnterpriseId(onlineBusinessSearchParam.getEnterpriseId());
        if (onlineBusiness == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无此企业信息");
        }
        return onlineBusiness;
    }

    @Override
    public void insertMessageByEnterpriseId(String json) {
        OnlineBusinessParm onlineBusinessParm = JSONObject.parseObject(json,OnlineBusinessParm.class);
        ValidationResult result = validator.validate(onlineBusinessParm);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        OnlineBusiness onlineBusiness = new OnlineBusiness();
        onlineBusiness.setOperateIp("124.124.124");
        onlineBusiness.setOperateTime(new Date());
        onlineBusiness.setOparetor("zcc");
        onlineBusiness.setId(onlineBusinessParm.getId());
        onlineBusiness.setSplat(onlineBusinessParm.getSplatList().toString());
        onlineBusiness.setAnswer(onlineBusinessParm.getAnswer());
        onlineBusiness.setEnterpriseId(onlineBusinessParm.getEnterpriseId());
        onlineBusiness.setName(onlineBusinessParm.getName());
        onlineBusiness.setAddress(onlineBusinessParm.getAddress());
        onlineBusiness.setPhone(onlineBusinessParm.getPhone());
        onlineBusiness.setExamFlag(onlineBusinessParm.getExamFlag());
        onlineBusiness.setEnterpriseIcon(onlineBusinessParm.getEnterpriseIcon());
        onlineBusiness.setOperationArea(onlineBusinessParm.getOperationArea());
        onlineBusiness.setLicense(onlineBusinessParm.getLicense());
        onlineBusiness.setMtBusinessLicense(onlineBusinessParm.getMtBusinessLicense());
        onlineBusiness.setMtFoodLicense(onlineBusinessParm.getMtFoodLicense());
        onlineBusiness.setMtFoodSafe(onlineBusinessParm.getMtFoodSafe());
        onlineBusiness.setMtHomePage(onlineBusinessParm.getMtHomePage());
        onlineBusiness.setElmBusinessLicence(onlineBusinessParm.getElmBusinessLicence());
        onlineBusiness.setElmFoodLicence(onlineBusinessParm.getElmFoodLicence());
        onlineBusiness.setElmFoodSafe(onlineBusinessParm.getElmFoodSafe());
        onlineBusiness.setElmHomePage(onlineBusinessParm.getElmHomePage());
        onlineBusiness.setBdBusinessLicence(onlineBusinessParm.getBdBusinessLicence());
        onlineBusiness.setBdFoodLicence(onlineBusinessParm.getBdFoodLicence());
        onlineBusiness.setBdFoodSafe(onlineBusinessParm.getBdFoodSafe());
        onlineBusiness.setBdHomePage(onlineBusinessParm.getBdHomePage());
        onlineBusiness.setOtherHomePage(onlineBusinessParm.getOtherHomePage());
        onlineBusiness.setOtherBusinessLicence(onlineBusinessParm.getOtherBusinessLicence());
        onlineBusiness.setOtherFoodLicence(onlineBusinessParm.getOtherBusinessLicence());
        onlineBusiness.setOtherFoodSafe(onlineBusinessParm.getOtherFoodSafe());

        OnlineBusiness onlineBusiness1 = onlineBusinessMapper.getMessageByEnterpriseId(onlineBusinessParm.getEnterpriseId());
        if (onlineBusiness1 == null) {    //新增
            onlineBusinessMapper.insert(onlineBusiness);
        } else {                          //修改（删除并添加）
            onlineBusinessMapper.deleteByPrimaryKey(onlineBusiness1.getId());
            onlineBusinessMapper.insert(onlineBusiness);
        }
        return;
    }

}