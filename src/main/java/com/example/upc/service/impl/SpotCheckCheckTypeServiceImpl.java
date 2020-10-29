package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SpotCheckCheckTypeMapper;
import com.example.upc.dataobject.SpotCheckCheckType;
import com.example.upc.service.SpotCheckCheckTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpotCheckCheckTypeServiceImpl implements SpotCheckCheckTypeService {
    @Autowired
    SpotCheckCheckTypeMapper spotCheckCheckTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=spotCheckCheckTypeMapper.countList();
        if (count > 0) {
            List<SpotCheckCheckType> fpList = spotCheckCheckTypeMapper.getPage(pageQuery);
            PageResult<SpotCheckCheckType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SpotCheckCheckType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SpotCheckCheckType spotCheckCheckType) {
        if(checkTypeExist(spotCheckCheckType.getType(), spotCheckCheckType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckCheckType spotCheckCheckType1 = new SpotCheckCheckType();
        spotCheckCheckType1.setType(spotCheckCheckType.getType());
        spotCheckCheckType1.setOperator("操作人");
        spotCheckCheckType1.setOperatorIp("124.124.124");
        spotCheckCheckType1.setOperatorTime(new Date());
        // TODO: sendEmail

        spotCheckCheckTypeMapper.insertSelective(spotCheckCheckType1);
    }
    @Override
    public void delete(int fpId) {
        SpotCheckCheckType spotCheckCheckType = spotCheckCheckTypeMapper.selectByPrimaryKey(fpId);
        if(spotCheckCheckType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckCheckTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(SpotCheckCheckType spotCheckCheckType) {
        if(checkTypeExist(spotCheckCheckType.getType(), spotCheckCheckType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckCheckType spotCheckCheckType1 = new SpotCheckCheckType();
        spotCheckCheckType1.setId(spotCheckCheckType.getId());
        spotCheckCheckType1.setType(spotCheckCheckType.getType());
        spotCheckCheckType1.setOperator("操作人");
        spotCheckCheckType1.setOperatorIp("124.124.124");
        spotCheckCheckType1.setOperatorTime(new Date());

        // TODO: sendEmail

        spotCheckCheckTypeMapper.updateByPrimaryKeySelective(spotCheckCheckType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return spotCheckCheckTypeMapper.countByType(type,id) > 0;
    }
}
