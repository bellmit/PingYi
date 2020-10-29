package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.InspectLargeConfParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.InspectLargeConfMapper;
import com.example.upc.dataobject.InspectLargeConf;
import com.example.upc.service.InspectLargeConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/5/18 18:58
 */
@Service
public class InspectLargeConfServiceImpl implements InspectLargeConfService {
    @Autowired
    private InspectLargeConfMapper inspectLargeConfMapper;

    @Override
    public PageResult<InspectLargeConfParam> getPage(PageQuery pageQuery) {
        int count=inspectLargeConfMapper.countList();
        if (count > 0) {
            List<InspectLargeConfParam> inspectIndustrieList = inspectLargeConfMapper.getPage(pageQuery);
            PageResult<InspectLargeConfParam> pageResult = new PageResult<>();
            pageResult.setData(inspectIndustrieList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectLargeConfParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(InspectLargeConf inspectLargeConf) {
        if(inspectLargeConfMapper.countByName(inspectLargeConf.getName(),inspectLargeConf.getIndustry())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同检查项");
        }
        inspectLargeConf.setOperateIp("124.124.124");
        inspectLargeConf.setOperator("操作人");
        inspectLargeConf.setOperateTime(new Date());
        inspectLargeConfMapper.insertSelective(inspectLargeConf);
    }

    @Override
    @Transactional
    public void update(InspectLargeConf inspectLargeConf) {
        InspectLargeConf before = inspectLargeConfMapper.selectByPrimaryKey(inspectLargeConf.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新检查项不存在");
        }
        inspectLargeConf.setOperateIp("124.124.124");
        inspectLargeConf.setOperator("操作人");
        inspectLargeConf.setOperateTime(new Date());
        inspectLargeConfMapper.updateByPrimaryKeySelective(inspectLargeConf);
    }

    @Override
    public void delete(int id) {
        InspectLargeConf before = inspectLargeConfMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除信息不存在");
        }
        inspectLargeConfMapper.deleteByPrimaryKey(id);
    }
    @Override
    public List<InspectLargeConf> getAll() {
        return inspectLargeConfMapper.getAll();
    }
}
