package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.InspectLllegalityConfMapper;
import com.example.upc.dataobject.InspectLllegalityConf;
import com.example.upc.service.InspectLllegalityConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/8/30 21:54
 */
@Service
public class InspectLllegalityConfServiceImpl implements InspectLllegalityConfService {
    @Autowired
    private InspectLllegalityConfMapper inspectLllegalityConfMapper;
    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=inspectLllegalityConfMapper.countList();
        if (count > 0) {
            List<InspectLllegalityConf> inspectLllegalityConfList = inspectLllegalityConfMapper.getPage(pageQuery);
            PageResult<InspectLllegalityConf> pageResult = new PageResult<>();
            pageResult.setData(inspectLllegalityConfList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectLllegalityConf> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<InspectLllegalityConf> getList() {
        return inspectLllegalityConfMapper.getList();
    }

    @Override
    @Transactional
    public void insert(InspectLllegalityConf inspectLllegalityConf) {
        inspectLllegalityConf.setOperateIp("124.214.124");
        inspectLllegalityConf.setOperateTime(new Date());
        inspectLllegalityConf.setOperator("操作人");
        inspectLllegalityConfMapper.insertSelective(inspectLllegalityConf);
    }

    @Override
    @Transactional
    public void update(InspectLllegalityConf inspectLllegalityConf) {
        InspectLllegalityConf before = inspectLllegalityConfMapper.selectByPrimaryKey(inspectLllegalityConf.getId());
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法更新");
        }
        inspectLllegalityConf.setOperateIp("124.214.124");
        inspectLllegalityConf.setOperateTime(new Date());
        inspectLllegalityConf.setOperator("操作人");
        inspectLllegalityConfMapper.updateByPrimaryKeySelective(inspectLllegalityConf);
    }

    @Override
    public void delete(int id) {
        InspectLllegalityConf before = inspectLllegalityConfMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectLllegalityConfMapper.deleteByPrimaryKey(id);
    }
}
