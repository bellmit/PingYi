package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.InspectBookConfMapper;
import com.example.upc.dataobject.InspectBookConf;
import com.example.upc.service.InspectBookConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/7/5 14:55
 */
@Service
public class InspectBookConfServiceImpl implements InspectBookConfService {
    @Autowired
    private InspectBookConfMapper inspectBookConfMapper;
    @Override
    public PageResult<InspectBookConf> getPage(PageQuery pageQuery) {
        int count= inspectBookConfMapper.countList();
        if (count > 0) {
            List<InspectBookConf> inspectBookConfList = inspectBookConfMapper.getPage(pageQuery);
            PageResult<InspectBookConf> pageResult = new PageResult<>();
            pageResult.setData(inspectBookConfList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectBookConf> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(InspectBookConf inspectLargeConf) {
        inspectLargeConf.setOperateIp("124.214.124");
        inspectLargeConf.setOperateTime(new Date());
        inspectLargeConf.setOperator("操作人");
        inspectBookConfMapper.insertSelective(inspectLargeConf);
    }

    @Override
    @Transactional
    public void update(InspectBookConf inspectLargeConf) {
        InspectBookConf before = inspectBookConfMapper.selectByPrimaryKey(inspectLargeConf.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新档案不存在");
        }
        inspectLargeConf.setOperateIp("124.214.124");
        inspectLargeConf.setOperateTime(new Date());
        inspectLargeConf.setOperator("操作人");
        inspectBookConfMapper.updateByPrimaryKeySelective(inspectLargeConf);
    }

    @Override
    public void delete(int id) {
        InspectBookConf before = inspectBookConfMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除档案不存在");
        }
        inspectBookConfMapper.deleteByPrimaryKey(id);
    }
}
