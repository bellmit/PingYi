package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dao.ComplaintProblemTypeTwoMapper;
import com.example.upc.dataobject.ComplaintProblemTypeTwo;
import com.example.upc.service.ComplaintProblemTypeTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ComplaintProblemTypeTwoServiceImpl implements ComplaintProblemTypeTwoService {
    @Autowired
    ComplaintProblemTypeTwoMapper complaintProblemTypeTwoMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam) {
        int count=complaintProblemTypeTwoMapper.countList(complaintSearchParam);
        if (count > 0) {
            List<ComplaintProblemTypeTwo> fpList = complaintProblemTypeTwoMapper.getPage(pageQuery,complaintSearchParam);
            PageResult<ComplaintProblemTypeTwo> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ComplaintProblemTypeTwo> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<ComplaintProblemTypeTwo> getListByOne(String One) {
        List<ComplaintProblemTypeTwo> list = complaintProblemTypeTwoMapper.getListByOne(One);
        return list;
    }

    @Override
    public List<ComplaintProblemTypeTwo> getPageList(ComplaintSearchParam complaintSearchParam) {
        List<ComplaintProblemTypeTwo> list = complaintProblemTypeTwoMapper.getPageList(complaintSearchParam);
        return list;
    }

    @Override
    public void insert(ComplaintProblemTypeTwo complaintProblemTypeTwo) {
        if(checkTypeExist(complaintProblemTypeTwo.getType(), complaintProblemTypeTwo.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintProblemTypeTwo complaintProblemTypeTwo1 = new ComplaintProblemTypeTwo();
        complaintProblemTypeTwo1.setOneType(complaintProblemTypeTwo.getOneType());
        complaintProblemTypeTwo1.setType(complaintProblemTypeTwo.getType());
        complaintProblemTypeTwo1.setOperator("操作人");
        complaintProblemTypeTwo1.setOperatorIp("124.124.124");
        complaintProblemTypeTwo1.setOperatorTime(new Date());
        // TODO: sendEmail

        complaintProblemTypeTwoMapper.insertSelective(complaintProblemTypeTwo1);
    }
    @Override
    public void delete(int fpId) {
        ComplaintProblemTypeTwo complaintProblemTypeTwo = complaintProblemTypeTwoMapper.selectByPrimaryKey(fpId);
        if(complaintProblemTypeTwo==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        complaintProblemTypeTwoMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(ComplaintProblemTypeTwo complaintProblemTypeTwo) {
        if(checkTypeExist(complaintProblemTypeTwo.getType(), complaintProblemTypeTwo.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        ComplaintProblemTypeTwo complaintProblemTypeTwo1 = new ComplaintProblemTypeTwo();
        complaintProblemTypeTwo1.setId(complaintProblemTypeTwo.getId());
        complaintProblemTypeTwo1.setOneType(complaintProblemTypeTwo.getOneType());
        complaintProblemTypeTwo1.setType(complaintProblemTypeTwo.getType());
        complaintProblemTypeTwo1.setOperator("操作人");
        complaintProblemTypeTwo1.setOperatorIp("124.124.124");
        complaintProblemTypeTwo1.setOperatorTime(new Date());

        // TODO: sendEmail

        complaintProblemTypeTwoMapper.updateByPrimaryKeySelective(complaintProblemTypeTwo1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return complaintProblemTypeTwoMapper.countByType(type,id) > 0;
    }

}
