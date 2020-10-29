package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dao.InspectRetificationNoticeMapper;
import com.example.upc.dataobject.InspectRetificationNotice;
import com.example.upc.service.InspectRetificationNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/8/30 21:59
 */
@Service
public class InspectRetificationNoticeServiceImpl implements InspectRetificationNoticeService {
    @Autowired
    private InspectRetificationNoticeMapper inspectRetificationNoticeMapper;
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public InspectRetificationNotice getByParentId(int id) {
        return inspectRetificationNoticeMapper.getByParentId(id);
    }

    @Override
    @Transactional
    public void insert(InspectRetificationNotice inspectRetificationNotice) {
        ValidationResult result = validator.validate(inspectRetificationNotice);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectRetificationNoticeMapper.countByParentId(inspectRetificationNotice.getParentId(),inspectRetificationNotice.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectRetificationNotice.setOperateIp("124.214.124");
        inspectRetificationNotice.setOperateTime(new Date());
        inspectRetificationNotice.setOperator("操作人");
        inspectRetificationNoticeMapper.insertSelective(inspectRetificationNotice);
        inspectDailyBookMapper.changeBookRemark("已办理",inspectRetificationNotice.getParentId());
    }

    @Override
    @Transactional
    public void update(InspectRetificationNotice inspectRetificationNotice) {
        ValidationResult result = validator.validate(inspectRetificationNotice);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectRetificationNoticeMapper.countByParentId(inspectRetificationNotice.getParentId(),inspectRetificationNotice.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectDailyBookMapper.changeBookRemark("已办理",inspectRetificationNotice.getParentId());
        InspectRetificationNotice before = inspectRetificationNoticeMapper.selectByPrimaryKey(inspectRetificationNotice.getId());
        if(before ==null){
            inspectRetificationNotice.setOperateIp("124.214.124");
            inspectRetificationNotice.setOperateTime(new Date());
            inspectRetificationNotice.setOperator("操作人");
            inspectRetificationNoticeMapper.insertSelective(inspectRetificationNotice);
            return;
        }
        inspectRetificationNotice.setOperateIp("124.214.124");
        inspectRetificationNotice.setOperateTime(new Date());
        inspectRetificationNotice.setOperator("操作人");
        inspectRetificationNoticeMapper.updateByPrimaryKeySelective(inspectRetificationNotice);
    }

    @Override
    public void delete(int id) {
        InspectRetificationNotice before = inspectRetificationNoticeMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectRetificationNoticeMapper.deleteByParentId(id);
    }
}
