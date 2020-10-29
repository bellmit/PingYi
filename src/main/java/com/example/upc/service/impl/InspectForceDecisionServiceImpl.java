package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dao.InspectForceDecisionMapper;
import com.example.upc.dataobject.InspectForceDecision;
import com.example.upc.service.InspectForceDecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/8/30 22:11
 */
@Service
public class InspectForceDecisionServiceImpl implements InspectForceDecisionService{
    @Autowired
    private InspectForceDecisionMapper inspectForceDecisionMapper;
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public InspectForceDecision getByParentId(int id) {
        return inspectForceDecisionMapper.getByParentId(id);
    }

    @Override
    @Transactional
    public void insert(InspectForceDecision inspectForceDecision) {
        ValidationResult result = validator.validate(inspectForceDecision);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectForceDecisionMapper.countByParentId(inspectForceDecision.getParentId(),inspectForceDecision.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectForceDecision.setOperateIp("124.214.124");
        inspectForceDecision.setOperateTime(new Date());
        inspectForceDecision.setOperator("操作人");
        inspectForceDecisionMapper.insertSelective(inspectForceDecision);
        inspectDailyBookMapper.changeBookRemark("已办理",inspectForceDecision.getParentId());
    }

    @Override
    @Transactional
    public void update(InspectForceDecision inspectForceDecision) {
        ValidationResult result = validator.validate(inspectForceDecision);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectForceDecisionMapper.countByParentId(inspectForceDecision.getParentId(),inspectForceDecision.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectDailyBookMapper.changeBookRemark("已办理",inspectForceDecision.getParentId());
        InspectForceDecision before = inspectForceDecisionMapper.selectByPrimaryKey(inspectForceDecision.getId());
        if(before ==null){
            inspectForceDecision.setOperateIp("124.214.124");
            inspectForceDecision.setOperateTime(new Date());
            inspectForceDecision.setOperator("操作人");
            inspectForceDecisionMapper.insertSelective(inspectForceDecision);
            return;
        }
        inspectForceDecision.setOperateIp("124.214.124");
        inspectForceDecision.setOperateTime(new Date());
        inspectForceDecision.setOperator("操作人");
        inspectForceDecisionMapper.updateByPrimaryKeySelective(inspectForceDecision);
    }

    @Override
    public void delete(int id) {
        InspectForceDecision before = inspectForceDecisionMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectForceDecisionMapper.deleteByParentId(id);
    }
}
