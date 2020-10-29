package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.dao.DistributionListMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.DistributionList;
import com.example.upc.dataobject.StartSelfInspection;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.DistributionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistributionListServiceImpl implements DistributionListService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private DistributionListMapper distributionListMapper;
    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void insert(DistributionList distributionList, SysUser sysUser){
        ValidationResult result = validator.validate(distributionList);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }
        distributionList.setEnterprise(sysUser.getInfoId());
        distributionList.setOperator(sysUser.getUsername());
        distributionList.setOperatorIp("124.124.124");
        distributionListMapper.insertSelective(distributionList);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void update(DistributionList distributionList, SysUser sysUser){
        ValidationResult result = validator.validate(distributionList);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(distributionListMapper.selectByPrimaryKey(distributionList.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        distributionList.setEnterprise(sysUser.getInfoId());
        distributionList.setOperator(sysUser.getUsername());
        distributionList.setOperatorIp("124.124.124");
        distributionListMapper.updateByPrimaryKeySelective(distributionList);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void delete(int id){
        DistributionList distributionList = distributionListMapper.selectByPrimaryKey(id);
        if(distributionList==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        distributionListMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public List<DistributionList> getByEnterpriseId (InspectionSearchParam inspectionSearchParam, int id){
        return distributionListMapper.getByEnterpriseId(id);
    }
}
