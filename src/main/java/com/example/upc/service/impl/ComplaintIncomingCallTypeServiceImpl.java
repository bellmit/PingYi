package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dao.ComplaintIncomingCallTypeMapper;
import com.example.upc.dataobject.ComplaintIncomingCallType;
import com.example.upc.service.ComplaintIncomingCallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintIncomingCallTypeServiceImpl implements ComplaintIncomingCallTypeService {
    @Autowired
    ComplaintIncomingCallTypeMapper complaintIncomingCallTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam) {
        int count=complaintIncomingCallTypeMapper.countList(complaintSearchParam);
        if (count > 0) {
            List<ComplaintIncomingCallType> fpList = complaintIncomingCallTypeMapper.getPage(pageQuery,complaintSearchParam);
            PageResult<ComplaintIncomingCallType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintIncomingCallType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<ComplaintIncomingCallType> getPageList(ComplaintSearchParam complaintSearchParam) {
        List<ComplaintIncomingCallType> list = complaintIncomingCallTypeMapper.getPageList(complaintSearchParam);
        return list;
    }

    @Override
    public void insert(ComplaintIncomingCallType complaintIncomingCallType) {
        if(checkTypeExist(complaintIncomingCallType.getType(), complaintIncomingCallType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintIncomingCallType complaintIncomingCallType1 = new ComplaintIncomingCallType();
        complaintIncomingCallType1.setType(complaintIncomingCallType.getType());
        complaintIncomingCallType1.setOperator("操作人");
        complaintIncomingCallType1.setOperatorIp("124.124.124");
        complaintIncomingCallType1.setOperatorTime(new Date());
        // TODO: sendEmail

        complaintIncomingCallTypeMapper.insertSelective(complaintIncomingCallType1);
    }
    @Override
    public void delete(int fpId) {
        ComplaintIncomingCallType complaintIncomingCallType = complaintIncomingCallTypeMapper.selectByPrimaryKey(fpId);
        if(complaintIncomingCallType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        complaintIncomingCallTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(ComplaintIncomingCallType complaintIncomingCallType) {
        if(checkTypeExist(complaintIncomingCallType.getType(), complaintIncomingCallType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintIncomingCallType complaintIncomingCallType1 = new ComplaintIncomingCallType();
        complaintIncomingCallType1.setId(complaintIncomingCallType.getId());
        complaintIncomingCallType1.setType(complaintIncomingCallType.getType());
        complaintIncomingCallType1.setOperator("操作人");
        complaintIncomingCallType1.setOperatorIp("124.124.124");
        complaintIncomingCallType1.setOperatorTime(new Date());

        // TODO: sendEmail

        complaintIncomingCallTypeMapper.updateByPrimaryKeySelective(complaintIncomingCallType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return complaintIncomingCallTypeMapper.countByType(type,id) > 0;
    }
}
