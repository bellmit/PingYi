package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.dao.InspectArriveEvidenceMapper;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dataobject.InspectArriveEvidence;
import com.example.upc.service.InspectArriveEvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/8/30 22:13
 */
@Service
public class InspectArriveEvidenceServiceImpl implements InspectArriveEvidenceService {
    @Autowired
    private InspectArriveEvidenceMapper inspectArriveEvidenceMapper;
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public InspectArriveEvidence getByParentId(int id) {
        return inspectArriveEvidenceMapper.getByParentId(id);
    }

    @Override
    @Transactional
    public void insert(InspectArriveEvidence inspectArriveEvidence) {
        ValidationResult result = validator.validate(inspectArriveEvidence);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectArriveEvidenceMapper.countByParentId(inspectArriveEvidence.getParentId(),inspectArriveEvidence.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectArriveEvidence.setOperateIp("124.214.124");
        inspectArriveEvidence.setOperateTime(new Date());
        inspectArriveEvidence.setOperator("操作人");
        inspectArriveEvidenceMapper.insertSelective(inspectArriveEvidence);
        inspectDailyBookMapper.changeBookRemark("已办理",inspectArriveEvidence.getParentId());
    }

    @Override
    @Transactional
    public void update(InspectArriveEvidence inspectArriveEvidence) {
        ValidationResult result = validator.validate(inspectArriveEvidence);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectArriveEvidenceMapper.countByParentId(inspectArriveEvidence.getParentId(),inspectArriveEvidence.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectDailyBookMapper.changeBookRemark("已办理",inspectArriveEvidence.getParentId());
        InspectArriveEvidence before = inspectArriveEvidenceMapper.selectByPrimaryKey(inspectArriveEvidence.getId());
        if(before ==null){
            inspectArriveEvidence.setOperateIp("124.214.124");
            inspectArriveEvidence.setOperateTime(new Date());
            inspectArriveEvidence.setOperator("操作人");
            inspectArriveEvidenceMapper.insertSelective(inspectArriveEvidence);
            return;
        }
        inspectArriveEvidence.setOperateIp("124.214.124");
        inspectArriveEvidence.setOperateTime(new Date());
        inspectArriveEvidence.setOperator("操作人");
        inspectArriveEvidenceMapper.updateByPrimaryKeySelective(inspectArriveEvidence);
    }

    @Override
    public void delete(int id) {
        InspectArriveEvidence before = inspectArriveEvidenceMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectArriveEvidenceMapper.deleteByParentId(id);
    }
}
