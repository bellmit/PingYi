package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.InspectClauseConfParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.InspectClauseConfMapper;
import com.example.upc.dataobject.InspectClauseConf;
import com.example.upc.service.InspectClauseConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/5/18 19:01
 */
@Service
public class InspectClauseConfServiceImpl implements InspectClauseConfService {
    @Autowired
    private InspectClauseConfMapper inspectClauseConfMapper;
    @Override
    public PageResult<InspectClauseConfParam> getPage(PageQuery pageQuery) {
        int count=inspectClauseConfMapper.countList();
        if (count > 0) {
            List<InspectClauseConfParam> inspectClauseConfList = inspectClauseConfMapper.getPage(pageQuery);
            PageResult<InspectClauseConfParam> pageResult = new PageResult<>();
            pageResult.setData(inspectClauseConfList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectClauseConfParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(InspectClauseConf inspectClauseConf) {
        if(inspectClauseConfMapper.countByName(inspectClauseConf.getClauseName(),inspectClauseConf.getLargeClass(),inspectClauseConf.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同检查内容");
        }
        inspectClauseConf.setOperateIp("124.124.124");
        inspectClauseConf.setOperator("操作人");
        inspectClauseConf.setOperateTime(new Date());
        inspectClauseConfMapper.insertSelective(inspectClauseConf);
    }

    @Override
    @Transactional
    public void update(InspectClauseConf inspectClauseConf) {
        InspectClauseConf before = inspectClauseConfMapper.selectByPrimaryKey(inspectClauseConf.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新检查内容不存在");
        }
        if(inspectClauseConfMapper.countByName(inspectClauseConf.getClauseName(),inspectClauseConf.getLargeClass(),inspectClauseConf.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同检查内容");
        }
        inspectClauseConf.setOperateIp("124.124.124");
        inspectClauseConf.setOperator("操作人");
        inspectClauseConf.setOperateTime(new Date());
        inspectClauseConfMapper.updateByPrimaryKeySelective(inspectClauseConf);
    }

    @Override
    public void delete(int id) {
        InspectClauseConf before = inspectClauseConfMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除信息不存在");
        }
        inspectClauseConfMapper.deleteByPrimaryKey(id);
    }
}
