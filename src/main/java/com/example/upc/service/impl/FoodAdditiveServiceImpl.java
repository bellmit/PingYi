package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.FoodAdditiveParam;
import com.example.upc.controller.searchParam.FoodAdditiveSearchParam;
import com.example.upc.dao.FoodAdditiveMapper;
import com.example.upc.dataobject.FoodAdditive;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FoodAdditiveService;
import com.example.upc.util.operateExcel.WasteExcel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FoodAdditiveServiceImpl implements FoodAdditiveService {
    @Autowired
    FoodAdditiveMapper foodAdditiveMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public List<FoodAdditiveParam> selectByDate(FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser){
        ValidationResult result = validator.validate(foodAdditiveSearchParam,"select");
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        foodAdditiveSearchParam.setEnterpriseId(sysUser.getInfoId());
        return foodAdditiveMapper.selectByDate(foodAdditiveSearchParam);
    }

    @Override
    public void insert(FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser){
        ValidationResult result = validator.validate(foodAdditiveSearchParam,"insert");
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        FoodAdditive foodAdditive = new FoodAdditive();
        foodAdditiveSearchParam.setEnterpriseId(sysUser.getInfoId());
        BeanUtils.copyProperties(foodAdditiveSearchParam,foodAdditive);
        foodAdditive.setOperator(sysUser.getLoginName());
        foodAdditive.setOperatorIp("127.0.0.1");
        foodAdditive.setOperatorTime(new Date());
        foodAdditiveMapper.insertSelective(foodAdditive);
    }

    @Override
    public void update(FoodAdditiveSearchParam foodAdditiveSearchParam,SysUser sysUser){
        ValidationResult result = validator.validate(foodAdditiveSearchParam,"insert");
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        FoodAdditive foodAdditive = new FoodAdditive();
        BeanUtils.copyProperties(foodAdditiveSearchParam,foodAdditive);
        foodAdditive.setEnterpriseId(sysUser.getInfoId());
        foodAdditive.setOperator(sysUser.getLoginName());
        foodAdditive.setOperatorIp("127.0.0.1");
        foodAdditive.setOperatorTime(new Date());
        foodAdditiveMapper.updateByPrimaryKey(foodAdditive);
    }

    @Override
    public void delete(FoodAdditiveSearchParam foodAdditiveSearchParam){
        if(foodAdditiveSearchParam.getId()==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"没有删除的目标");
        }
        foodAdditiveMapper.deleteByPrimaryKey(foodAdditiveSearchParam.getId());
    }

    @Override
    public Object standingFoodAdditive(FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser) throws IOException{
        foodAdditiveSearchParam.setEnterpriseId(sysUser.getInfoId());
        List<FoodAdditiveParam> foodAdditiveParamList = foodAdditiveMapper.selectByDate(foodAdditiveSearchParam);
        List<String[]> data = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        foodAdditiveParamList.forEach(item->{
            data.add(new String[]{
                    dateFormat.format(item.getUseDate()),item.getSafeManager(),item.getAdditiveName(),item.getUseAmount().toString(),item.getUseRange(),item.getAdditiveRatio()
                    ,item.getGotPerson(),item.getProduceCompany(),dateFormat2.format(item.getProduceDate()),item.getRemark()
            });
        });
        String fileName = "食品添加剂";
        String path = WasteExcel.getXLsx(data,"/template/【导出】食品添加剂使用模板.xlsx",fileName,sysUser.getInfoId());
        return path;
    }
}
