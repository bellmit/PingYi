package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.TypeSearchParam;
import com.example.upc.dao.FormatOriginFoodMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.FormatOriginFood;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatOriginFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FormatOriginFoodServiceImpl implements FormatOriginFoodService {
    @Autowired
    FormatOriginFoodMapper formatOriginFoodMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, TypeSearchParam typeSearchParam, SysUser sysUser) {
        int count=formatOriginFoodMapper.countList(typeSearchParam,sysUser.getInfoId());
        if (count > 0) {
            List<FormatOriginFood> fofList = formatOriginFoodMapper.getPage(pageQuery, sysUser.getInfoId(), typeSearchParam);
            PageResult<FormatOriginFood> pageResult = new PageResult<>();
            pageResult.setData(fofList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatOriginFood> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(FormatOriginFood formatOriginFood, SysUser sysUser) {

        if(checkNameExist(formatOriginFood.getType(), formatOriginFood.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        FormatOriginFood formatOriginFood1 = new FormatOriginFood();
        formatOriginFood1.setEnterpriseId(supervisionEnterprise.getId());
        formatOriginFood1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatOriginFood1.setAreaId(supervisionEnterprise.getArea());
        formatOriginFood1.setType(formatOriginFood.getType());
        formatOriginFood1.setOperator("操作人");
        formatOriginFood1.setOperatorIp("124.124.124");
        formatOriginFood1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginFoodMapper.insertSelective(formatOriginFood1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }
    @Override
    public void delete(int fofId) {
        FormatOriginFood formatOriginFood = formatOriginFoodMapper.selectByPrimaryKey(fofId);
        if(formatOriginFood==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatOriginFoodMapper.deleteByPrimaryKey(fofId);
    }

    @Override
    @Transactional
    public void update(FormatOriginFood formatOriginFood, SysUser sysUser) {
        if(formatOriginFoodMapper.selectByPrimaryKey(formatOriginFood.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkNameExist(formatOriginFood.getType(), formatOriginFood.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        FormatOriginFood formatOriginFood1 = new FormatOriginFood();
        formatOriginFood1.setId(formatOriginFood.getId());
        formatOriginFood1.setEnterpriseId(supervisionEnterprise.getId());
        formatOriginFood1.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
        formatOriginFood1.setAreaId(supervisionEnterprise.getArea());
        formatOriginFood1.setType(formatOriginFood.getType());
        formatOriginFood1.setOperator("操作人");
        formatOriginFood1.setOperatorIp("124.124.124");
        formatOriginFood1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginFoodMapper.updateByPrimaryKeySelective(formatOriginFood1);
    }

    public boolean checkNameExist(String type,Integer id) {
        return formatOriginFoodMapper.countByName(type,id) > 0;
    }

}
