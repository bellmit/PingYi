package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.TypeSearchParam;
import com.example.upc.dao.FormatOriginMapper;
import com.example.upc.dataobject.FormatOrigin;
import com.example.upc.service.FormatOriginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FormatOriginServiceImpl implements FormatOriginService {
    @Autowired
    FormatOriginMapper formatOriginMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, TypeSearchParam typeSearchParam) {
        int count=formatOriginMapper.countList(typeSearchParam);
        if (count > 0) {
            List<FormatOrigin> foList = formatOriginMapper.getPage(pageQuery, typeSearchParam);
            PageResult<FormatOrigin> pageResult = new PageResult<>();
            pageResult.setData(foList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatOrigin> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<FormatOrigin> getOrigin(PageQuery pageQuery, TypeSearchParam typeSearchParam) {
        int count=formatOriginMapper.countList(typeSearchParam);
        if (count > 0) {
            pageQuery.setPageSize(count);
            return formatOriginMapper.getPage(pageQuery, typeSearchParam);
        }
        return null;
    }

    @Override
    @Transactional
    public void insert(FormatOrigin formatOrigin) {

        ValidationResult result = validator.validate(formatOrigin);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkNameExist(formatOrigin.getType(),formatOrigin.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        FormatOrigin formatOrigin1 = new FormatOrigin();
        formatOrigin1.setType(formatOrigin.getType());
        formatOrigin1.setExplains(formatOrigin.getExplains());
        formatOrigin1.setOperator("操作人");
        formatOrigin1.setOperatorIp("124.124.124");
        formatOrigin1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginMapper.insertSelective(formatOrigin1);
    }
    @Override
    public void delete(int foId) {
        FormatOrigin formatOrigin = formatOriginMapper.selectByPrimaryKey(foId);
        if(formatOrigin==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatOriginMapper.deleteByPrimaryKey(foId);
    }

    @Override
    @Transactional
    public void update(FormatOrigin formatOrigin) {

        ValidationResult result = validator.validate(formatOrigin);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatOriginMapper.selectByPrimaryKey(formatOrigin.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkNameExist(formatOrigin.getType(),formatOrigin.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        FormatOrigin formatOrigin1 = new FormatOrigin();
        formatOrigin1.setId(formatOrigin.getId());
        formatOrigin1.setType(formatOrigin.getType());
        formatOrigin1.setExplains(formatOrigin.getExplains());
        formatOrigin1.setOperator("操作人");
        formatOrigin1.setOperatorIp("124.124.124");
        formatOrigin1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginMapper.updateByPrimaryKeySelective(formatOrigin1);
    }

    public boolean checkNameExist(String type,Integer id) {
        return formatOriginMapper.countByName(type, id) > 0;
    }

}
