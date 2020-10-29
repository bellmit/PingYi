package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.QuickSpotCheckResultTypeMapper;
import com.example.upc.dataobject.QuickSpotCheckResultType;
import com.example.upc.service.QuickSpotCheckResultTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuickSpotCheckResultTypeServiceImpl implements QuickSpotCheckResultTypeService {
    @Autowired
    QuickSpotCheckResultTypeMapper quickSpotCheckResultTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=quickSpotCheckResultTypeMapper.countList();
        if (count > 0) {
            List<QuickSpotCheckResultType> fpList = quickSpotCheckResultTypeMapper.getPage(pageQuery);
            PageResult<QuickSpotCheckResultType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<QuickSpotCheckResultType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(QuickSpotCheckResultType quickSpotCheckResultType) {
        if(checkTypeExist(quickSpotCheckResultType.getType(), quickSpotCheckResultType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        QuickSpotCheckResultType quickSpotCheckResultType1 = new QuickSpotCheckResultType();
        quickSpotCheckResultType1.setType(quickSpotCheckResultType.getType());
        quickSpotCheckResultType1.setOperator("操作人");
        quickSpotCheckResultType1.setOperatorIp("124.124.124");
        quickSpotCheckResultType1.setOperatorTime(new Date());
        // TODO: sendEmail

        quickSpotCheckResultTypeMapper.insertSelective(quickSpotCheckResultType1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int id) {
        QuickSpotCheckResultType quickSpotCheckResultType = quickSpotCheckResultTypeMapper.selectByPrimaryKey(id);
        if(quickSpotCheckResultType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        quickSpotCheckResultTypeMapper.deleteByPrimaryKey(id);
    }
    @Override
    public void update(QuickSpotCheckResultType quickSpotCheckResultType) {
        if(checkTypeExist(quickSpotCheckResultType.getType(), quickSpotCheckResultType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        QuickSpotCheckResultType quickSpotCheckResultType1 = new QuickSpotCheckResultType();
        quickSpotCheckResultType1.setId(quickSpotCheckResultType.getId());
        quickSpotCheckResultType1.setType(quickSpotCheckResultType.getType());
        quickSpotCheckResultType1.setOperator("操作人");
        quickSpotCheckResultType1.setOperatorIp("124.124.124");
        quickSpotCheckResultType1.setOperatorTime(new Date());

        // TODO: sendEmail

        quickSpotCheckResultTypeMapper.updateByPrimaryKeySelective(quickSpotCheckResultType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return quickSpotCheckResultTypeMapper.countByType(type,id) > 0;
    }
}
