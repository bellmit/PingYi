package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SpotCheckStepTypeMapper;
import com.example.upc.dataobject.SpotCheckStepType;
import com.example.upc.service.SpotCheckStepTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpotCheckStepTypeServiceImpl implements SpotCheckStepTypeService {
    @Autowired
    SpotCheckStepTypeMapper spotCheckStepTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=spotCheckStepTypeMapper.countList();
        if (count > 0) {
            List<SpotCheckStepType> fpList = spotCheckStepTypeMapper.getPage(pageQuery);
            PageResult<SpotCheckStepType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SpotCheckStepType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SpotCheckStepType spotCheckStepType) {
        if(checkTypeExist(spotCheckStepType.getType(), spotCheckStepType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckStepType spotCheckStepType1 = new SpotCheckStepType();
        spotCheckStepType1.setType(spotCheckStepType.getType());
        spotCheckStepType1.setOperator("操作人");
        spotCheckStepType1.setOperatorIp("124.124.124");
        spotCheckStepType1.setOperatorTime(new Date());
        // TODO: sendEmail

        spotCheckStepTypeMapper.insertSelective(spotCheckStepType1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        SpotCheckStepType spotCheckStepType = spotCheckStepTypeMapper.selectByPrimaryKey(fpId);
        if(spotCheckStepType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckStepTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(SpotCheckStepType spotCheckStepType) {
        if(checkTypeExist(spotCheckStepType.getType(), spotCheckStepType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckStepType spotCheckStepType1 = new SpotCheckStepType();
        spotCheckStepType1.setId(spotCheckStepType.getId());
        spotCheckStepType1.setType(spotCheckStepType.getType());
        spotCheckStepType1.setOperator("操作人");
        spotCheckStepType1.setOperatorIp("124.124.124");
        spotCheckStepType1.setOperatorTime(new Date());

        // TODO: sendEmail

        spotCheckStepTypeMapper.updateByPrimaryKeySelective(spotCheckStepType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return spotCheckStepTypeMapper.countByType(type,id) > 0;
    }
}
