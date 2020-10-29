package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dao.ComplaintEmergencyTypeMapper;
import com.example.upc.dataobject.ComplaintEmergencyType;
import com.example.upc.service.ComplaintEmergencyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintEmergencyTypeServiceImpl implements ComplaintEmergencyTypeService {
    @Autowired
    ComplaintEmergencyTypeMapper complaintEmergencyTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam) {
        int count=complaintEmergencyTypeMapper.countList(complaintSearchParam);
        if (count > 0) {
            List<ComplaintEmergencyType> fpList = complaintEmergencyTypeMapper.getPage(pageQuery,complaintSearchParam);
            PageResult<ComplaintEmergencyType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintEmergencyType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<ComplaintEmergencyType> getPageList(ComplaintSearchParam complaintSearchParam) {
        List<ComplaintEmergencyType> list = complaintEmergencyTypeMapper.getPageList(complaintSearchParam);
        return list;
    }

    @Override
    public void insert(ComplaintEmergencyType complaintEmergencyType) {
        if(checkTypeExist(complaintEmergencyType.getType(), complaintEmergencyType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintEmergencyType complaintEmergencyType1 = new ComplaintEmergencyType();
        complaintEmergencyType1.setType(complaintEmergencyType.getType());
        complaintEmergencyType1.setOperator("操作人");
        complaintEmergencyType1.setOperatorIp("124.124.124");
        complaintEmergencyType1.setOperatorTime(new Date());
        // TODO: sendEmail

        complaintEmergencyTypeMapper.insertSelective(complaintEmergencyType1);
    }
    @Override
    public void delete(int fpId) {
        ComplaintEmergencyType spotCheckStepType = complaintEmergencyTypeMapper.selectByPrimaryKey(fpId);
        if(spotCheckStepType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        complaintEmergencyTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(ComplaintEmergencyType complaintEmergencyType) {
        if(checkTypeExist(complaintEmergencyType.getType(), complaintEmergencyType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintEmergencyType complaintEmergencyType1 = new ComplaintEmergencyType();
        complaintEmergencyType1.setId(complaintEmergencyType.getId());
        complaintEmergencyType1.setType(complaintEmergencyType.getType());
        complaintEmergencyType1.setOperator("操作人");
        complaintEmergencyType1.setOperatorIp("124.124.124");
        complaintEmergencyType1.setOperatorTime(new Date());

        // TODO: sendEmail

        complaintEmergencyTypeMapper.updateByPrimaryKeySelective(complaintEmergencyType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return complaintEmergencyTypeMapper.countByType(type,id) > 0;
    }
}
