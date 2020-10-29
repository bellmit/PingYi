package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dao.ComplaintInformationComeTypeMapper;
import com.example.upc.dataobject.ComplaintInformationComeType;
import com.example.upc.service.ComplaintInformationComeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintInformationComeTypeServiceImpl implements ComplaintInformationComeTypeService {
    @Autowired
    ComplaintInformationComeTypeMapper complaintInformationComeTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam) {
        int count=complaintInformationComeTypeMapper.countList(complaintSearchParam);
        if (count > 0) {
            List<ComplaintInformationComeType> fpList = complaintInformationComeTypeMapper.getPage(pageQuery,complaintSearchParam);
            PageResult<ComplaintInformationComeType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintInformationComeType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<ComplaintInformationComeType> getPageList(ComplaintSearchParam complaintSearchParam) {
        List<ComplaintInformationComeType> list = complaintInformationComeTypeMapper.getPageList(complaintSearchParam);
        return list;
    }

    @Override
    public void insert(ComplaintInformationComeType complaintInformationComeType) {
        if(checkTypeExist(complaintInformationComeType.getType(), complaintInformationComeType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintInformationComeType complaintInformationComeType1 = new ComplaintInformationComeType();
        complaintInformationComeType1.setType(complaintInformationComeType.getType());
        complaintInformationComeType1.setOperator("操作人");
        complaintInformationComeType1.setOperatorIp("124.124.124");
        complaintInformationComeType1.setOperatorTime(new Date());
        // TODO: sendEmail

        complaintInformationComeTypeMapper.insertSelective(complaintInformationComeType1);
    }
    @Override
    public void delete(int fpId) {
        ComplaintInformationComeType complaintIncomingCallType = complaintInformationComeTypeMapper.selectByPrimaryKey(fpId);
        if(complaintIncomingCallType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        complaintInformationComeTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(ComplaintInformationComeType complaintInformationComeType) {
        if(checkTypeExist(complaintInformationComeType.getType(), complaintInformationComeType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintInformationComeType complaintInformationComeType1 = new ComplaintInformationComeType();
        complaintInformationComeType1.setId(complaintInformationComeType.getId());
        complaintInformationComeType1.setType(complaintInformationComeType.getType());
        complaintInformationComeType1.setOperator("操作人");
        complaintInformationComeType1.setOperatorIp("124.124.124");
        complaintInformationComeType1.setOperatorTime(new Date());

        // TODO: sendEmail

        complaintInformationComeTypeMapper.updateByPrimaryKeySelective(complaintInformationComeType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return complaintInformationComeTypeMapper.countByType(type,id) > 0;
    }
}
