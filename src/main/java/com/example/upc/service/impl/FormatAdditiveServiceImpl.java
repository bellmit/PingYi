package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.FormatAdditiveParam;
import com.example.upc.controller.param.FormatAdditiveSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.AdditiveSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatAdditiveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormatAdditiveServiceImpl implements FormatAdditiveService {
    @Autowired
    FormatAdditiveMapper formatAdditiveMapper;
    @Autowired
    FormatAdditiveConfigMapper formatAdditiveConfigMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    SupervisionGaMapper supervisionGaMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, AdditiveSearchParam additiveSearchParam) {
        int count=formatAdditiveMapper.countListSup(additiveSearchParam);
        if (count > 0) {
            List<FormatAdditiveSupParam> faList = formatAdditiveMapper.getPage(pageQuery, additiveSearchParam);
            PageResult<FormatAdditiveSupParam> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatAdditiveSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, AdditiveSearchParam additiveSearchParam) {

        int count=formatAdditiveMapper.countListEnterprise(id, additiveSearchParam);
        if (count > 0) {
            List<FormatAdditiveSupParam> fdList = formatAdditiveMapper.getPageEnterprise(pageQuery,id,additiveSearchParam);
            PageResult<FormatAdditiveSupParam> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatAdditiveSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, AdditiveSearchParam additiveSearchParam) {
        int count=formatAdditiveMapper.countListAdmin(additiveSearchParam);
        if (count > 0) {
            List<FormatAdditiveSupParam> faList = formatAdditiveMapper.getPageAdmin(pageQuery, additiveSearchParam);
            PageResult<FormatAdditiveSupParam> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatAdditiveSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {
        FormatAdditiveParam formatAdditiveParam = JSONObject.parseObject(json,FormatAdditiveParam.class);
        FormatAdditive formatAdditive = new FormatAdditive();
        BeanUtils.copyProperties(formatAdditiveParam,formatAdditive);

        ValidationResult result = validator.validate(formatAdditive);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(sysUser.getUserType()==1) {
            SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());//找到该企业
            formatAdditive.setEnterprise(sysUser.getInfoId());
            formatAdditive.setArea(supervisionEnterprise.getArea());
            if (supervisionEnterprise.getSpName() == null)//找到该企业安全责任员
            {
                formatAdditive.setAdministrator("暂无");
            } else {
                formatAdditive.setAdministrator(supervisionEnterprise.getSpName());//赋入
            }
        }
        else
        {
            formatAdditive.setEnterprise(formatAdditiveParam.getEnterprise());
            SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(formatAdditiveParam.getEnterprise());//找到该企业
            formatAdditive.setArea(formatAdditiveParam.getArea());
            formatAdditive.setAdministrator(supervisionEnterprise.getSpName());
        }
        formatAdditive.setOperatorIp("124.124.124");
        formatAdditive.setOperatorTime(new Date());
        formatAdditive.setOperator("zcc");
        formatAdditiveMapper.insertSelective(formatAdditive);//插入
        if(formatAdditive.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        formatAdditiveConfigMapper.deleteByParentId(formatAdditive.getId());//子表插入
        List<FormatAdditiveConfig> formatAdditiveConfigList = formatAdditiveParam.getList();
        if(formatAdditiveConfigList.size()>0){
            formatAdditiveConfigMapper.batchInsert(formatAdditiveConfigList.stream().map((recordConfig)->{

                ValidationResult result1 = validator.validate(recordConfig);
                if(result1.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result1.getErrMsg());
                }

                recordConfig.setOperatorIp("124.124.124");
                recordConfig.setOperatorTime(new Date());
                recordConfig.setOperator("zcc");
                recordConfig.setParentId(formatAdditive.getId());
                return recordConfig;}).collect(Collectors.toList()));
        }

        // TODO: sendEmail

    }
    @Override
    public void delete(int faId) {
        FormatAdditive formatAdditive = formatAdditiveMapper.selectByPrimaryKey(faId);
        if(formatAdditive==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatAdditiveMapper.deleteByPrimaryKey(faId);
        formatAdditiveConfigMapper.deleteByParentId(faId);
    }

    @Override
    @Transactional
    public void update(String json,SysUser sysUser) {
        FormatAdditiveParam formatAdditiveParam = JSONObject.parseObject(json,FormatAdditiveParam.class);
        FormatAdditive formatAdditive = new FormatAdditive();
        BeanUtils.copyProperties(formatAdditiveParam,formatAdditive);

        ValidationResult result = validator.validate(formatAdditive);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatAdditive before = formatAdditiveMapper.selectByPrimaryKey(formatAdditive.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新项目不存在");
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        formatAdditive.setEnterprise(sysUser.getInfoId());
        formatAdditive.setArea(supervisionEnterprise.getArea());
        if (supervisionEnterprise.getSpName()==null)
        {
            formatAdditive.setAdministrator("暂无");
        }
        else
        {
            formatAdditive.setAdministrator(supervisionEnterprise.getSpName());
        }
        formatAdditive.setOperatorIp("124.124.124");
        formatAdditive.setOperatorTime(new Date());
        formatAdditive.setOperator("zcc");
        formatAdditiveConfigMapper.deleteByParentId(formatAdditive.getId());
        List<FormatAdditiveConfig> formatAdditiveConfigList = formatAdditiveParam.getList();
        if(formatAdditiveConfigList.size()>0){
            formatAdditiveConfigMapper.batchInsert(formatAdditiveConfigList.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatAdditive.getId());
                return list;}).collect(Collectors.toList()));
        }
        formatAdditiveMapper.updateByPrimaryKeySelective(formatAdditive);
    }


    @Override
    public FormatAdditiveParam getById(int id) {
        FormatAdditive formatAdditive = formatAdditiveMapper.selectByPrimaryKey(id);
        FormatAdditiveParam formatAdditiveParam = new FormatAdditiveParam();
        BeanUtils.copyProperties(formatAdditive, formatAdditiveParam);
        List<FormatAdditiveConfig> list = formatAdditiveConfigMapper.selectByParentId(formatAdditive.getId());
        formatAdditiveParam.setList(list);
        return formatAdditiveParam;
    }

    @Override
    @Transactional
    public void updateRecord(int id, String publicity)
    {
        FormatAdditive before = formatAdditiveMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新项目不存在");
        }
        formatAdditiveMapper.updateRecord(id,publicity);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    public SupervisionGa getGa(int id)
    {
        SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(id);
        return supervisionGa;
    }
}
