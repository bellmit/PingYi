package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysIndustryMapper;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.service.SysIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/5/18 19:00
 */
@Service
public class SysIndustryServiceImpl implements SysIndustryService {
    @Autowired
    private SysIndustryMapper sysIndustryMapper;

    @Override
    public PageResult<SysIndustry> getPage(PageQuery pageQuery) {
        int count= sysIndustryMapper.countList();
        if (count > 0) {
            List<SysIndustry> inspectIndustrieList = sysIndustryMapper.getPage(pageQuery);
            PageResult<SysIndustry> pageResult = new PageResult<>();
            pageResult.setData(inspectIndustrieList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysIndustry> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<SysIndustry> getAll() {
        return sysIndustryMapper.getList();
    }

    @Override
    @Transactional
    public void insert(SysIndustry sysIndustry) {
        if(sysIndustryMapper.countByName(sysIndustry.getName(),sysIndustry.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同行业类别");
        }
        sysIndustry.setOperateIp("124.124.124");
        sysIndustry.setOperator("操作人");
        sysIndustry.setOperateTime(new Date());
        sysIndustryMapper.insertSelective(sysIndustry);
    }

    @Override
    @Transactional
    public void update(SysIndustry sysIndustry) {
        SysIndustry before = sysIndustryMapper.selectByPrimaryKey(sysIndustry.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新行业类别不存在");
        }
        if(sysIndustryMapper.countByName(sysIndustry.getName(),sysIndustry.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同名称行业类别");
        }
        sysIndustry.setOperateIp("124.124.124");
        sysIndustry.setOperator("操作人");
        sysIndustry.setOperateTime(new Date());
        sysIndustryMapper.updateByPrimaryKeySelective(sysIndustry);
    }

    @Override
    public void delete(int id) {
        SysIndustry before = sysIndustryMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除信息不存在");
        }
        sysIndustryMapper.deleteByPrimaryKey(id);
    }
}
