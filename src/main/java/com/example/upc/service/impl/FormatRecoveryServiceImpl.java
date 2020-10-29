package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dao.FormatRecoveryMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.FormatRecovery;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FormatRecoveryServiceImpl implements FormatRecoveryService {
    @Autowired
    FormatRecoveryMapper formatRecoveryMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam, SysUser sysUser) {
        int count=formatRecoveryMapper.countList(measurementSearchParam, sysUser.getInfoId());
        if (count > 0) {
            List<FormatRecovery> fqtList = formatRecoveryMapper.getPage(pageQuery, measurementSearchParam, sysUser.getInfoId());
            PageResult<FormatRecovery> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatRecovery> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<FormatRecovery> getPage2 ( MeasurementSearchParam measurementSearchParam, SysUser sysUser){
        int count=formatRecoveryMapper.countList(measurementSearchParam, sysUser.getInfoId());
        if (count > 0) {
            List<FormatRecovery> fqtList = formatRecoveryMapper.getPage2( measurementSearchParam, sysUser.getInfoId());
            return fqtList;
        }else {
            List<FormatRecovery> pageResult = new ArrayList<>();
            return pageResult;
        }
    }

    @Override
    @Transactional
    public void insert(FormatRecovery formatRecovery, SysUser sysUser) {

        ValidationResult result = validator.validate(formatRecovery);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkAllExist(sysUser.getInfoId(), formatRecovery.getRecoveryEnterprise(), formatRecovery.getCharger(), formatRecovery.getAddress(), formatRecovery.getPhone(),formatRecovery.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        FormatRecovery formatRecovery1 = new FormatRecovery();
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        formatRecovery1.setEnterpriseId(supervisionEnterprise.getId());
        formatRecovery1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatRecovery1.setAreaId(supervisionEnterprise.getArea());
        formatRecovery1.setRecoveryEnterprise(formatRecovery.getRecoveryEnterprise());
        formatRecovery1.setCharger(formatRecovery.getCharger());
        formatRecovery1.setAddress(formatRecovery.getAddress());
        formatRecovery1.setPhone(formatRecovery.getPhone());
        formatRecovery1.setOperator("操作人");
        formatRecovery1.setOperatorIp("124.124.124");
        formatRecovery1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatRecoveryMapper.insertSelective(formatRecovery1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }
    @Override
    public void delete(int fqtId) {
        FormatRecovery formatRecovery = formatRecoveryMapper.selectByPrimaryKey(fqtId);
        if(formatRecovery==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatRecoveryMapper.deleteByPrimaryKey(fqtId);
    }

    @Override
    @Transactional
    public void update(FormatRecovery formatRecovery, SysUser sysUser) {

        ValidationResult result = validator.validate(formatRecovery);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatRecoveryMapper.selectByPrimaryKey(formatRecovery.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkAllExist(sysUser.getInfoId(), formatRecovery.getRecoveryEnterprise(), formatRecovery.getCharger(), formatRecovery.getAddress(), formatRecovery.getPhone(),formatRecovery.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        FormatRecovery formatRecovery1 = new FormatRecovery();
        formatRecovery1.setId(formatRecovery.getId());
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        formatRecovery1.setEnterpriseId(supervisionEnterprise.getId());
        formatRecovery1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatRecovery1.setAreaId(supervisionEnterprise.getArea());
        formatRecovery1.setRecoveryEnterprise(formatRecovery.getRecoveryEnterprise());
        formatRecovery1.setCharger(formatRecovery.getCharger());
        formatRecovery1.setAddress(formatRecovery.getAddress());
        formatRecovery1.setPhone(formatRecovery.getPhone());
        formatRecovery1.setOperator("操作人");
        formatRecovery1.setOperatorIp("124.124.124");
        formatRecovery1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatRecoveryMapper.updateByPrimaryKeySelective(formatRecovery1);
    }

    public boolean checkAllExist(int eid, String enterprise, String charger, String address, String phone, Integer id) {
        return formatRecoveryMapper.countByAll(eid, enterprise, charger, address, phone, id) > 0;
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

}
