package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dao.SpecialEquipmentIndustryMapper;
import com.example.upc.dataobject.SpecialEquipmentIndustry;
import com.example.upc.service.SpecialEquipmentIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class SpecialEquipmentIndustryServiceImpl implements SpecialEquipmentIndustryService {
    @Autowired
    SpecialEquipmentIndustryMapper specialEquipmentIndustryMapper;
    @Autowired
    private ValidatorImpl validator;


    @Override
    public PageResult getPage(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam) {
        int count=specialEquipmentIndustryMapper.countList(measurementSearchParam);
        if (count > 0) {
            List<SpecialEquipmentIndustry> fqtList = specialEquipmentIndustryMapper.getPage(pageQuery, measurementSearchParam);
            PageResult<SpecialEquipmentIndustry> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpecialEquipmentIndustry> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SpecialEquipmentIndustry specialEquipmentIndustry) {

        ValidationResult result = validator.validate(specialEquipmentIndustry);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkAllExist(specialEquipmentIndustry.getName(),specialEquipmentIndustry.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        SpecialEquipmentIndustry specialEquipmentIndustry1 = new SpecialEquipmentIndustry();
        specialEquipmentIndustry1.setName(specialEquipmentIndustry.getName());
        specialEquipmentIndustry1.setOperator("操作人");
        specialEquipmentIndustry1.setOperatorIp("124.124.124");
        specialEquipmentIndustry1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentIndustryMapper.insertSelective(specialEquipmentIndustry1);
    }

    @Override
    public void delete(int id) {
        SpecialEquipmentIndustry specialEquipmentIndustry = specialEquipmentIndustryMapper.selectByPrimaryKey(id);
        if(specialEquipmentIndustry==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        specialEquipmentIndustryMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void update(SpecialEquipmentIndustry specialEquipmentIndustry) {
        if(specialEquipmentIndustryMapper.selectByPrimaryKey(specialEquipmentIndustry.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkAllExist(specialEquipmentIndustry.getName(),specialEquipmentIndustry.getId())) {
            throw new BusinessException(EmBusinessError.MEASUREMENT_NAME);
        }

        SpecialEquipmentIndustry specialEquipmentIndustry1 = new SpecialEquipmentIndustry();
        specialEquipmentIndustry1.setId(specialEquipmentIndustry.getId());
        specialEquipmentIndustry1.setName(specialEquipmentIndustry.getName());
        specialEquipmentIndustry1.setOperator("操作人");
        specialEquipmentIndustry1.setOperatorIp("124.124.124");
        specialEquipmentIndustry1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentIndustryMapper.updateByPrimaryKeySelective(specialEquipmentIndustry1);
    }

    public boolean checkAllExist(String name, Integer id) {
        return specialEquipmentIndustryMapper.countByName(name, id) > 0;
    }
}
