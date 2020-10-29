package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SpotCheckDateTypeMapper;
import com.example.upc.dataobject.SpotCheckDateType;
import com.example.upc.service.SpotCheckDateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpotCheckDateTypeServiceImpl implements SpotCheckDateTypeService {
    @Autowired
    SpotCheckDateTypeMapper spotCheckDateTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=spotCheckDateTypeMapper.countList();
        if (count > 0) {
            List<SpotCheckDateType> fpList = spotCheckDateTypeMapper.getPage(pageQuery);
            PageResult<SpotCheckDateType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SpotCheckDateType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SpotCheckDateType spotCheckDateType) {
        if(checkTypeExist(spotCheckDateType.getType(), spotCheckDateType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckDateType spotCheckDateType1 = new SpotCheckDateType();
        spotCheckDateType1.setType(spotCheckDateType.getType());
        spotCheckDateType1.setOperator("操作人");
        spotCheckDateType1.setOperatorIp("124.124.124");
        spotCheckDateType1.setOperatorTime(new Date());
        // TODO: sendEmail

        spotCheckDateTypeMapper.insertSelective(spotCheckDateType1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        SpotCheckDateType spotCheckDateType = spotCheckDateTypeMapper.selectByPrimaryKey(fpId);
        if(spotCheckDateType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckDateTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(SpotCheckDateType spotCheckDateType) {
        if(checkTypeExist(spotCheckDateType.getType(), spotCheckDateType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckDateType spotCheckDateType1 = new SpotCheckDateType();
        spotCheckDateType1.setId(spotCheckDateType.getId());
        spotCheckDateType1.setType(spotCheckDateType.getType());
        spotCheckDateType1.setOperator("操作人");
        spotCheckDateType1.setOperatorIp("124.124.124");
        spotCheckDateType1.setOperatorTime(new Date());

        // TODO: sendEmail

        spotCheckDateTypeMapper.updateByPrimaryKeySelective(spotCheckDateType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return spotCheckDateTypeMapper.countByType(type,id) > 0;
    }
}
