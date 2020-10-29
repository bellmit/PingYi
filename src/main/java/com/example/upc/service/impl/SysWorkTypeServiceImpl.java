package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.SysWorkTypeParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysWorkTypeMapper;
import com.example.upc.dataobject.SysWorkType;
import com.example.upc.service.SysWorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 20:49
 */
@Service
public class SysWorkTypeServiceImpl implements SysWorkTypeService {

    @Autowired
    private SysWorkTypeMapper sysWorkTypeMapper;
    @Override
    public PageResult<SysWorkTypeParam> getPage(PageQuery pageQuery) {
        int count= sysWorkTypeMapper.countList();
        if (count > 0) {
            List<SysWorkTypeParam> workTypeList = sysWorkTypeMapper.getPage(pageQuery);
            PageResult<SysWorkTypeParam> pageResult = new PageResult<>();
            pageResult.setData(workTypeList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysWorkTypeParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SysWorkType sysWorkType) {
         if(sysWorkTypeMapper.countByIndustryAndName(sysWorkType.getIndustryId(), sysWorkType.getName(),sysWorkType.getId())>0){
             throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同工作类别");
         }
         sysWorkType.setOperateIp("124.214.124");
         sysWorkType.setOperator("操作人");
         sysWorkType.setOperateTime(new Date());
         sysWorkTypeMapper.insertSelective(sysWorkType);
    }

    @Override
    @Transactional
    public void update(SysWorkType sysWorkType) {
        SysWorkType before = sysWorkTypeMapper.selectByPrimaryKey(sysWorkType.getId());
        if(before == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新工作类别不存在");
        }
        if(sysWorkTypeMapper.countByIndustryAndName(sysWorkType.getIndustryId(), sysWorkType.getName(),sysWorkType.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同工作类别");
        }
        sysWorkType.setOperateIp("124.214.124");
        sysWorkType.setOperator("操作人");
        sysWorkType.setOperateTime(new Date());
        sysWorkTypeMapper.updateByPrimaryKeySelective(sysWorkType);
    }

    @Override
    public void delete(int id) {
        SysWorkType sysWorkType = sysWorkTypeMapper.selectByPrimaryKey(id);
        if(sysWorkType ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的不存在，无法删除");
        }
        sysWorkTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysWorkType> getAll() {
        return sysWorkTypeMapper.getList();
    }
}
