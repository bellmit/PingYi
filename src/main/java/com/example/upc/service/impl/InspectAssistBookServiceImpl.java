package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.dao.InspectAssistBookMapper;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dataobject.InspectAssistBook;
import com.example.upc.service.InspectAssistBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zcc
 * @date 2019/8/30 22:12
 */
@Service
public class InspectAssistBookServiceImpl implements InspectAssistBookService{
    @Autowired
    private InspectAssistBookMapper inspectAssistBookMapper;
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public InspectAssistBook getByParentId(int id) {
        return inspectAssistBookMapper.getByParentId(id);
    }

    @Override
    @Transactional
    public void insert(InspectAssistBook inspectAssistBook) {
        ValidationResult result = validator.validate(inspectAssistBook);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectAssistBookMapper.countByParentId(inspectAssistBook.getParentId(),inspectAssistBook.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectAssistBook.setOperateIp("124.214.124");
        inspectAssistBook.setOperateTime(new Date());
        inspectAssistBook.setOperator("操作人");
        inspectAssistBookMapper.insertSelective(inspectAssistBook);
        inspectDailyBookMapper.changeBookRemark("已办理",inspectAssistBook.getParentId());
    }

    @Override
    @Transactional
    public void update(InspectAssistBook inspectAssistBook) {
        ValidationResult result = validator.validate(inspectAssistBook);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectAssistBookMapper.countByParentId(inspectAssistBook.getParentId(),inspectAssistBook.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        inspectDailyBookMapper.changeBookRemark("已办理",inspectAssistBook.getParentId());
        InspectAssistBook before = inspectAssistBookMapper.selectByPrimaryKey(inspectAssistBook.getId());
        if(before ==null){
            inspectAssistBook.setOperateIp("124.214.124");
            inspectAssistBook.setOperateTime(new Date());
            inspectAssistBook.setOperator("操作人");
            inspectAssistBookMapper.insertSelective(inspectAssistBook);
            return;
        }
        inspectAssistBook.setOperateIp("124.214.124");
        inspectAssistBook.setOperateTime(new Date());
        inspectAssistBook.setOperator("操作人");
        inspectAssistBookMapper.updateByPrimaryKeySelective(inspectAssistBook);
    }

    @Override
    public void delete(int id) {
        InspectAssistBook before = inspectAssistBookMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectAssistBookMapper.deleteByParentId(id);
    }
}
