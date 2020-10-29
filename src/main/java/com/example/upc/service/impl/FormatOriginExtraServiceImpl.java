package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.OriginExtraSearchParam;
import com.example.upc.dao.FormatOriginExtraMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.FormatOriginExtra;
import com.example.upc.dataobject.FormatOriginExtraParam;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatOriginExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FormatOriginExtraServiceImpl implements FormatOriginExtraService {
    @Autowired
    FormatOriginExtraMapper formatOriginExtraMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, OriginExtraSearchParam originExtraSearchParam, SysUser sysUser) {
        int count=formatOriginExtraMapper.countList(originExtraSearchParam,sysUser.getInfoId());
        if (count > 0) {
            List<FormatOriginExtra> foList = formatOriginExtraMapper.getPage(pageQuery,sysUser.getInfoId(), originExtraSearchParam);
            PageResult<FormatOriginExtra> pageResult = new PageResult<>();
            pageResult.setData(foList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatOriginExtra> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<FormatOriginExtra> getPageByListId (OriginExtraSearchParam originExtraSearchParam, SysUser sysUser){
        int count=formatOriginExtraMapper.countList(originExtraSearchParam,sysUser.getInfoId());
        if (count > 0) {
            List<FormatOriginExtra> foList = formatOriginExtraMapper.getPage2(sysUser.getInfoId(), originExtraSearchParam);
            return foList;
        }
        List<FormatOriginExtra> pageResult = new ArrayList<>();
        return pageResult;
    }


    @Override
    @Transactional
    public void insert(FormatOriginExtra formatOriginExtra, SysUser sysUser) {

        ValidationResult result = validator.validate(formatOriginExtra);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        FormatOriginExtra formatOriginExtra1 = new FormatOriginExtra();
        formatOriginExtra1.setEnterpriseId(supervisionEnterprise.getId());
        formatOriginExtra1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatOriginExtra1.setAreaId(supervisionEnterprise.getArea());
        formatOriginExtra1.setMaterialcategory(formatOriginExtra.getMaterialcategory());
        formatOriginExtra1.setMaterialname(formatOriginExtra.getMaterialname());
        formatOriginExtra1.setSpecifications(formatOriginExtra.getSpecifications());
        formatOriginExtra1.setManufacturer(formatOriginExtra.getManufacturer());
        formatOriginExtra1.setBrand(formatOriginExtra.getBrand());
        formatOriginExtra1.setState(formatOriginExtra.getState());
        formatOriginExtra1.setExtra(formatOriginExtra.getExtra());
        formatOriginExtra1.setOperator("操作人");
        formatOriginExtra1.setOperatorIp("124.124.124");
        formatOriginExtra1.setListId(formatOriginExtra.getListId());
        formatOriginExtra1.setQualityGuaranteePeriod(formatOriginExtra.getQualityGuaranteePeriod());
        formatOriginExtra1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginExtraMapper.insertSelective(formatOriginExtra1);
    }
    @Override
    public void delete(int foId) {
        FormatOriginExtra formatOriginExtra = formatOriginExtraMapper.selectByPrimaryKey(foId);
        if(formatOriginExtra==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatOriginExtraMapper.deleteByPrimaryKey(foId);
    }

    @Override
    @Transactional
    public void update(FormatOriginExtra formatOriginExtra, SysUser sysUser) {

        ValidationResult result = validator.validate(formatOriginExtra);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatOriginExtraMapper.selectByPrimaryKey(formatOriginExtra.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        FormatOriginExtra formatOriginExtra1 = new FormatOriginExtra();
        formatOriginExtra1.setId(formatOriginExtra.getId());
        formatOriginExtra1.setEnterpriseId(supervisionEnterprise.getId());
        formatOriginExtra1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatOriginExtra1.setAreaId(supervisionEnterprise.getArea());
        formatOriginExtra1.setMaterialcategory(formatOriginExtra.getMaterialcategory());
        formatOriginExtra1.setMaterialname(formatOriginExtra.getMaterialname());
        formatOriginExtra1.setSpecifications(formatOriginExtra.getSpecifications());
        formatOriginExtra1.setManufacturer(formatOriginExtra.getManufacturer());
        formatOriginExtra1.setBrand(formatOriginExtra.getBrand());
        formatOriginExtra1.setState(formatOriginExtra.getState());
        formatOriginExtra1.setExtra(formatOriginExtra.getExtra());
        formatOriginExtra1.setOperator("操作人");
        formatOriginExtra1.setOperatorIp("124.124.124");
        formatOriginExtra1.setOperatorTime(new Date());
        // TODO: sendEmail

        formatOriginExtraMapper.updateByPrimaryKeySelective(formatOriginExtra1);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void insertList(List<FormatOriginExtra> formatOriginExtraList, SysUser sysUser){
        int listId = formatOriginExtraList.get(0).getListId();
        formatOriginExtraMapper.deleteByListId(listId);
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        Date date = new Date();
        for (FormatOriginExtra item:formatOriginExtraList
             ) {
            ValidationResult result = validator.validate(item);
            if(result.isHasErrors()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
            }
            item.setEnterpriseId(supervisionEnterprise.getId());
            item.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
            item.setAreaId(supervisionEnterprise.getArea());
            item.setOperator(sysUser.getUsername());
            item.setOperatorIp("124.124.124");
            item.setOperatorTime(date);

            formatOriginExtraMapper.insertSelective(item);
        }
    }

    @Override
    @Transactional
    public void updateList(List<FormatOriginExtra> formatOriginExtraList, SysUser sysUser){
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        Date date = new Date();
        for (FormatOriginExtra item:formatOriginExtraList
        ) {
            ValidationResult result = validator.validate(item);
            if(result.isHasErrors()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
            }
            item.setEnterpriseId(supervisionEnterprise.getId());
            item.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
            item.setAreaId(supervisionEnterprise.getArea());
            item.setOperator(sysUser.getUsername());
            item.setOperatorIp("124.124.124");
            item.setOperatorTime(date);

            formatOriginExtraMapper.updateByPrimaryKeySelective(item);
        }
    }
}
