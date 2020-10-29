package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SpotCheckDisposalTypeMapper;
import com.example.upc.dataobject.SpotCheckDisposalType;
import com.example.upc.service.SpotCheckDisposalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpotCheckDisposalTypeServiceImpl implements SpotCheckDisposalTypeService{
    @Autowired
    SpotCheckDisposalTypeMapper spotCheckDisposalTypeMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=spotCheckDisposalTypeMapper.countList();
        if (count > 0) {
            List<SpotCheckDisposalType> fpList = spotCheckDisposalTypeMapper.getPage(pageQuery);
            PageResult<SpotCheckDisposalType> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SpotCheckDisposalType> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SpotCheckDisposalType spotCheckDisposalType) {
        if(checkTypeExist(spotCheckDisposalType.getType(), spotCheckDisposalType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckDisposalType spotCheckDisposalType1 = new SpotCheckDisposalType();
        spotCheckDisposalType1.setType(spotCheckDisposalType.getType());
        spotCheckDisposalType1.setOperator("操作人");
        spotCheckDisposalType1.setOperatorIp("124.124.124");
        spotCheckDisposalType1.setOperatorTime(new Date());
        // TODO: sendEmail

        spotCheckDisposalTypeMapper.insertSelective(spotCheckDisposalType1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        SpotCheckDisposalType spotCheckDisposalType = spotCheckDisposalTypeMapper.selectByPrimaryKey(fpId);
        if(spotCheckDisposalType==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckDisposalTypeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(SpotCheckDisposalType spotCheckDisposalType) {
        if(checkTypeExist(spotCheckDisposalType.getType(), spotCheckDisposalType.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_TYPE);
        }

        SpotCheckDisposalType spotCheckDisposalType1 = new SpotCheckDisposalType();
        spotCheckDisposalType1.setId(spotCheckDisposalType.getId());
        spotCheckDisposalType1.setType(spotCheckDisposalType.getType());
        spotCheckDisposalType1.setOperator("操作人");
        spotCheckDisposalType1.setOperatorIp("124.124.124");
        spotCheckDisposalType1.setOperatorTime(new Date());

        // TODO: sendEmail

        spotCheckDisposalTypeMapper.updateByPrimaryKeySelective(spotCheckDisposalType1);
    }

    public boolean checkTypeExist(String type,Integer id) {
        return spotCheckDisposalTypeMapper.countByType(type,id) > 0;
    }
}
