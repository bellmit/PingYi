package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SpotCheckFoodTypeMapper;
import com.example.upc.dataobject.SpotCheckFoodType;
import com.example.upc.service.SpotCheckFoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpotCheckFoodTypeServiceImpl implements SpotCheckFoodTypeService {
    @Autowired
    SpotCheckFoodTypeMapper spotCheckFoodTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=spotCheckFoodTypeMapper.countList();
        if (count > 0) {
            List<SpotCheckFoodType> fpList = spotCheckFoodTypeMapper.getPage(pageQuery);
            PageResult<SpotCheckFoodType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SpotCheckFoodType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SpotCheckFoodType spotCheckFoodType) {
        if(checkTypeExist(spotCheckFoodType.getType(), spotCheckFoodType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckFoodType spotCheckFoodType1 = new SpotCheckFoodType();
        spotCheckFoodType1.setType(spotCheckFoodType.getType());
        spotCheckFoodType1.setOperator("操作人");
        spotCheckFoodType1.setOperatorIp("124.124.124");
        spotCheckFoodType1.setOperatorTime(new Date());
        // TODO: sendEmail

        spotCheckFoodTypeMapper.insertSelective(spotCheckFoodType1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        SpotCheckFoodType spotCheckFoodType = spotCheckFoodTypeMapper.selectByPrimaryKey(fpId);
        if(spotCheckFoodType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckFoodTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(SpotCheckFoodType spotCheckFoodType) {
        if(checkTypeExist(spotCheckFoodType.getType(), spotCheckFoodType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckFoodType spotCheckFoodType1 = new SpotCheckFoodType();
        spotCheckFoodType1.setId(spotCheckFoodType.getId());
        spotCheckFoodType1.setType(spotCheckFoodType.getType());
        spotCheckFoodType1.setOperator("操作人");
        spotCheckFoodType1.setOperatorIp("124.124.124");
        spotCheckFoodType1.setOperatorTime(new Date());

        // TODO: sendEmail

        spotCheckFoodTypeMapper.updateByPrimaryKeySelective(spotCheckFoodType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return spotCheckFoodTypeMapper.countByType(type,id) > 0;
    }
}
