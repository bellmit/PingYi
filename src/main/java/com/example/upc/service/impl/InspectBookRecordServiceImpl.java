package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.dao.InspectBookRecordMapper;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dataobject.InspectBookRecord;
import com.example.upc.service.InspectBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/8/30 21:52
 */
@Service
public class InspectBookRecordServiceImpl implements InspectBookRecordService{
    @Autowired
    private InspectBookRecordMapper inspectBookRecordMapper;
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public InspectBookRecord getByParentId(int id) {
        return inspectBookRecordMapper.getByParentId(id);
    }

    @Override
    @Transactional
    public void insert(InspectBookRecord inspectBookRecord) {
        ValidationResult result = validator.validate(inspectBookRecord);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectBookRecordMapper.countByParentId(inspectBookRecord.getParentId(),inspectBookRecord.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectBookRecord.setOperateIp("124.214.124");
        inspectBookRecord.setOperateTime(new Date());
        inspectBookRecord.setOperator("操作人");
        inspectBookRecordMapper.insertSelective(inspectBookRecord);
        inspectDailyBookMapper.changeBookRemark("已办理",inspectBookRecord.getParentId());
    }

    @Override
    @Transactional
    public void update(InspectBookRecord inspectBookRecord) {
        ValidationResult result = validator.validate(inspectBookRecord);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectBookRecordMapper.countByParentId(inspectBookRecord.getParentId(),inspectBookRecord.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectDailyBookMapper.changeBookRemark("已办理",inspectBookRecord.getParentId());
        InspectBookRecord before = inspectBookRecordMapper.selectByPrimaryKey(inspectBookRecord.getId());
        if(before ==null){
            inspectBookRecord.setOperateIp("124.214.124");
            inspectBookRecord.setOperateTime(new Date());
            inspectBookRecord.setOperator("操作人");
            inspectBookRecordMapper.insertSelective(inspectBookRecord);
            return;
        }
        inspectBookRecord.setOperateIp("124.214.124");
        inspectBookRecord.setOperateTime(new Date());
        inspectBookRecord.setOperator("操作人");
        inspectBookRecordMapper.updateByPrimaryKeySelective(inspectBookRecord);
    }

    @Override
    public void delete(int id) {
        InspectBookRecord before = inspectBookRecordMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectBookRecordMapper.deleteByParentId(id);
    }
}
