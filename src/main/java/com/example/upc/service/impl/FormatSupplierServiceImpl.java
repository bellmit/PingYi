package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.FormatSupplierParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.SupplierSearchParam;
import com.example.upc.dao.FormatOriginRecordExMapper;
import com.example.upc.dao.FormatSupplierMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatSupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FormatSupplierServiceImpl implements FormatSupplierService {
    @Autowired
    FormatSupplierMapper formatSupplierMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    FormatOriginRecordExMapper formatOriginRecordExMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, SupplierSearchParam supplierSearchParam, SysUser sysUser) {
        int count=formatSupplierMapper.countList(supplierSearchParam,sysUser.getInfoId());
        if (count > 0) {
            List<FormatSupplier> fsList = formatSupplierMapper.getPage(pageQuery, sysUser.getInfoId(), supplierSearchParam);
            PageResult<FormatSupplier> pageResult = new PageResult<>();
            pageResult.setData(fsList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatSupplier> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<FormatSupplier> getPage2 ( SupplierSearchParam supplierSearchParam, SysUser sysUser){
        int count=formatSupplierMapper.countList(supplierSearchParam,sysUser.getInfoId());
        if (count > 0) {
            List<FormatSupplier> fsList = formatSupplierMapper.getPage2( sysUser.getInfoId(), supplierSearchParam);
            return fsList;
        }
        List<FormatSupplier> pageResult = new ArrayList<>();
        return pageResult;
    }

    @Override
    public PageResult getPageSup(PageQuery pageQuery, SupplierSearchParam supplierSearchParam) {
        int count=formatSupplierMapper.countListSup(supplierSearchParam);
        if (count > 0) {
            List<FormatSupplier> fsList = formatSupplierMapper.getPageSup(pageQuery,  supplierSearchParam);
            PageResult<FormatSupplier> pageResult = new PageResult<>();
            pageResult.setData(fsList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatSupplier> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, SupplierSearchParam supplierSearchParam) {
        int count=formatSupplierMapper.countListAdmin(supplierSearchParam);
        if (count > 0) {
            List<FormatSupplier> fsList = formatSupplierMapper.getPageAdmin(pageQuery, supplierSearchParam);
            PageResult<FormatSupplier> pageResult = new PageResult<>();
            pageResult.setData(fsList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatSupplier> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(FormatSupplierParam formatSupplierParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatSupplierParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkAllExist(sysUser.getInfoId(), formatSupplierParam.getStype(), formatSupplierParam.getAddress(), formatSupplierParam.getName(), formatSupplierParam.getLicense(), formatSupplierParam.getNumber(),formatSupplierParam.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        FormatSupplier formatSupplier1 = new FormatSupplier();
        BeanUtils.copyProperties(formatSupplierParam,formatSupplier1);
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        formatSupplier1.setEnterpriseId(supervisionEnterprise.getId());
        formatSupplier1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatSupplier1.setAreaId(supervisionEnterprise.getArea());
        formatSupplier1.setStart(new Date());
        formatSupplier1.setEnd(new Date());
        formatSupplier1.setOperator("操作人");
        formatSupplier1.setOperatorIp("124.124.124");
        formatSupplier1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatSupplierMapper.insertSelective(formatSupplier1);
    }
    @Override
    public void delete(int fsId) {
        FormatSupplier formatSupplier = formatSupplierMapper.selectByPrimaryKey(fsId);
        if(formatSupplier==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatSupplierMapper.deleteByPrimaryKey(fsId);
    }

    @Override
    @Transactional
    public void update(FormatSupplierParam formatSupplierParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatSupplierParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatSupplierMapper.selectByPrimaryKey(formatSupplierParam.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        if(checkAllExist(sysUser.getInfoId(), formatSupplierParam.getStype(), formatSupplierParam.getAddress(), formatSupplierParam.getName(), formatSupplierParam.getLicense(), formatSupplierParam.getNumber(),formatSupplierParam.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        FormatSupplier formatSupplier1 = new FormatSupplier();
        BeanUtils.copyProperties(formatSupplierParam,formatSupplier1);
        formatSupplier1.setStart(new Date());
        formatSupplier1.setEnd(new Date());
        formatSupplier1.setOperator("操作人");
        formatSupplier1.setOperatorIp("124.124.124");
        formatSupplier1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatSupplierMapper.updateByPrimaryKeySelective(formatSupplier1);
    }
    @Override
    public FormatSupplier selectByName(String name)
    {
        FormatSupplier formatSupplier=formatSupplierMapper.selectByName(name);
        if (formatSupplier==null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERNO);
        }
        else
        {
            return formatSupplier;
        }
    }

    @Override
    public FormatSupplier selectById(Integer id, SysUser sysUser)
    {
        FormatOriginRecordEx formatOriginRecordEx=formatOriginRecordExMapper.selectByPrimaryKey(id);
        if (formatOriginRecordEx==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"条目不存在");
        }
        else
        {
            FormatSupplier formatSupplier = formatSupplierMapper.selectByInfo(sysUser.getInfoId(),formatOriginRecordEx.getSupplier(),formatOriginRecordEx.getSupplierType());
            if (formatSupplier==null)
            {
                throw new BusinessException(EmBusinessError.CHECK_USERNO);
            }
            else
            {
                return formatSupplier;
            }
        }
    }

    public boolean checkAllExist(int eid, String stype, String address, String name, String license, String number, Integer id) {
        return formatSupplierMapper.countByAll(eid,stype, address, name, license,number, id) > 0;
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

}
