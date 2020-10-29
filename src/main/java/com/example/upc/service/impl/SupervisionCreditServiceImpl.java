package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionCreditMapper;
import com.example.upc.dataobject.SupervisionCredit;
import com.example.upc.service.SupervisionCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:28
 */
@Service
public class SupervisionCreditServiceImpl implements SupervisionCreditService {
    @Autowired
    private SupervisionCreditMapper supervisionCreditMapper;
    @Override
    public PageResult<SupervisionCredit> getPage(PageQuery pageQuery) {
        int count= supervisionCreditMapper.countList();
        if (count > 0) {
            List<SupervisionCredit> supervisionCreditList = supervisionCreditMapper.getPage(pageQuery);
            PageResult<SupervisionCredit> pageResult = new PageResult<>();
            pageResult.setData(supervisionCreditList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionCredit> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionCredit supervisionCredit) {
        supervisionCredit.setOperateIp("124.214.124");
        supervisionCredit.setOperateTime(new Date());
        supervisionCredit.setOperator("操作人");
           supervisionCreditMapper.insertSelective(supervisionCredit);
    }

    @Override
    @Transactional
    public void update(SupervisionCredit supervisionCredit) {
        SupervisionCredit before = supervisionCreditMapper.selectByPrimaryKey(supervisionCredit.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新档案不存在");
        }
        supervisionCredit.setOperateIp("124.214.124");
        supervisionCredit.setOperateTime(new Date());
        supervisionCredit.setOperator("操作人");
        supervisionCreditMapper.updateByPrimaryKeySelective(supervisionCredit);
    }

    @Override
    public void delete(int id) {
        SupervisionCredit before = supervisionCreditMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除档案不存在");
        }
        supervisionCreditMapper.deleteByPrimaryKey(id);
    }

}
