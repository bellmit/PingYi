package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dao.ComplaintProblemTypeOneMapper;
import com.example.upc.dataobject.ComplaintProblemTypeOne;
import com.example.upc.service.ComplaintProblemTypeOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintProblemTypeOneServiceImpl implements ComplaintProblemTypeOneService {
    @Autowired
    ComplaintProblemTypeOneMapper complaintProblemTypeOneMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam) {
        int count=complaintProblemTypeOneMapper.countList(complaintSearchParam);
        if (count > 0) {
            List<ComplaintProblemTypeOne> fpList = complaintProblemTypeOneMapper.getPage(pageQuery,complaintSearchParam);
            PageResult<ComplaintProblemTypeOne> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintProblemTypeOne> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<ComplaintProblemTypeOne> getPageList(ComplaintSearchParam complaintSearchParam) {
        List<ComplaintProblemTypeOne> list = complaintProblemTypeOneMapper.getPageList(complaintSearchParam);
        return list;
    }

    @Override
    public void insert(ComplaintProblemTypeOne complaintProblemTypeOne) {
        if(checkTypeExist(complaintProblemTypeOne.getType(), complaintProblemTypeOne.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintProblemTypeOne complaintProblemTypeOne1 = new ComplaintProblemTypeOne();
        complaintProblemTypeOne1.setType(complaintProblemTypeOne.getType());
        complaintProblemTypeOne1.setOperator("操作人");
        complaintProblemTypeOne1.setOperatorIp("124.124.124");
        complaintProblemTypeOne1.setOperatorTime(new Date());
        // TODO: sendEmail

        complaintProblemTypeOneMapper.insertSelective(complaintProblemTypeOne1);
    }
    @Override
    public void delete(int fpId) {
        ComplaintProblemTypeOne complaintProblemTypeOne = complaintProblemTypeOneMapper.selectByPrimaryKey(fpId);
        if(complaintProblemTypeOne==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        complaintProblemTypeOneMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(ComplaintProblemTypeOne complaintProblemTypeOne) {
        if(checkTypeExist(complaintProblemTypeOne.getType(), complaintProblemTypeOne.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintProblemTypeOne complaintProblemTypeOne1 = new ComplaintProblemTypeOne();
        complaintProblemTypeOne1.setId(complaintProblemTypeOne.getId());
        complaintProblemTypeOne1.setType(complaintProblemTypeOne.getType());
        complaintProblemTypeOne1.setOperator("操作人");
        complaintProblemTypeOne1.setOperatorIp("124.124.124");
        complaintProblemTypeOne1.setOperatorTime(new Date());

        // TODO: sendEmail

        complaintProblemTypeOneMapper.updateByPrimaryKeySelective(complaintProblemTypeOne1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return complaintProblemTypeOneMapper.countByType(type,id) > 0;
    }
}
