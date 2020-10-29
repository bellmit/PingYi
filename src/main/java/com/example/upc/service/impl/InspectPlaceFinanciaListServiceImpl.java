package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.InspectPlaceFinancialListParam;
import com.example.upc.dao.InspectDailyBookMapper;
import com.example.upc.dao.InspectPlaceFinancialListMapper;
import com.example.upc.dataobject.InspectPlaceFinancialList;
import com.example.upc.dataobject.InspectThingList;
import com.example.upc.service.InspectPlaceFinancialListService;
import com.example.upc.service.InspectThingListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/8/30 22:00
 */
@Service
public class InspectPlaceFinanciaListServiceImpl implements InspectPlaceFinancialListService {
    @Autowired
    private InspectPlaceFinancialListMapper inspectPlaceFinancialListMapper;
    @Autowired
    private InspectThingListService inspectThingListService;
    @Autowired
    private InspectDailyBookMapper inspectDailyBookMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public InspectPlaceFinancialListParam getByParentId(int id) {
        InspectPlaceFinancialList inspectPlaceFinancialList = inspectPlaceFinancialListMapper.getByParentId(id);
        List<InspectThingList> inspectThingListList = inspectThingListService.getListByParentId(id);
        InspectPlaceFinancialListParam inspectPlaceFinancialListParam = new InspectPlaceFinancialListParam();
        if(inspectPlaceFinancialList==null){
            return null;
        }
        BeanUtils.copyProperties(inspectPlaceFinancialList,inspectPlaceFinancialListParam);
        inspectPlaceFinancialListParam.setInspectThingListList(inspectThingListList);
        return inspectPlaceFinancialListParam;
    }

    @Override
    @Transactional
    public void insert(InspectPlaceFinancialListParam inspectPlaceFinancialListParam) {
        ValidationResult result = validator.validate(inspectPlaceFinancialListParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectPlaceFinancialListMapper.countByParentId(inspectPlaceFinancialListParam.getParentId(),inspectPlaceFinancialListParam.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        InspectPlaceFinancialList inspectPlaceFinancialList = new InspectPlaceFinancialListParam();
        BeanUtils.copyProperties(inspectPlaceFinancialListParam,inspectPlaceFinancialList);
        inspectPlaceFinancialList.setOperateIp("124.214.124");
        inspectPlaceFinancialList.setOperateTime(new Date());
        inspectPlaceFinancialList.setOperator("操作人");
        inspectPlaceFinancialListMapper.insertSelective(inspectPlaceFinancialList);
        inspectThingListService.batchInsert(inspectPlaceFinancialListParam.getInspectThingListList(),inspectPlaceFinancialListParam.getParentId());
        inspectDailyBookMapper.changeBookRemark("已办理",inspectPlaceFinancialListParam.getParentId());
    }

    @Override
    @Transactional
    public void update(InspectPlaceFinancialListParam inspectPlaceFinancialListParam) {
        ValidationResult result = validator.validate(inspectPlaceFinancialListParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectPlaceFinancialListMapper.countByParentId(inspectPlaceFinancialListParam.getParentId(),inspectPlaceFinancialListParam.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该文书");
        }
        InspectPlaceFinancialList inspectPlaceFinancialList = new InspectPlaceFinancialListParam();
        BeanUtils.copyProperties(inspectPlaceFinancialListParam,inspectPlaceFinancialList);
        inspectPlaceFinancialList.setOperateIp("124.214.124");
        inspectPlaceFinancialList.setOperateTime(new Date());
        inspectPlaceFinancialList.setOperator("操作人");
        inspectThingListService.batchInsert(inspectPlaceFinancialListParam.getInspectThingListList(),inspectPlaceFinancialListParam.getParentId());
        inspectDailyBookMapper.changeBookRemark("已办理",inspectPlaceFinancialListParam.getParentId());
        InspectPlaceFinancialList before = inspectPlaceFinancialListMapper.selectByPrimaryKey(inspectPlaceFinancialListParam.getId());
        if(before ==null){
            inspectPlaceFinancialListMapper.insertSelective(inspectPlaceFinancialList);
            return;
        }
        inspectPlaceFinancialListMapper.updateByPrimaryKeySelective(inspectPlaceFinancialList);
    }

    @Override
    public void delete(int id) {
        InspectPlaceFinancialList before = inspectPlaceFinancialListMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectPlaceFinancialListMapper.deleteByParentId(id);
        inspectThingListService.batchDelete(id);
    }
}
