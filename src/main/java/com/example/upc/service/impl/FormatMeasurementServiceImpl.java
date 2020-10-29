package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dao.FormatMeasurementMapper;
import com.example.upc.dao.FormatQualityTimeMapper;
import com.example.upc.dataobject.FormatMeasurement;
import com.example.upc.dataobject.FormatQualityTime;
import com.example.upc.service.FormatMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FormatMeasurementServiceImpl implements FormatMeasurementService {
    @Autowired
    FormatMeasurementMapper formatMeasurementMapper;
    @Override
    public PageResult getPage(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam) {
        int count=formatMeasurementMapper.countList(measurementSearchParam);
        if (count > 0) {
            List<FormatMeasurement> fqtList = formatMeasurementMapper.getPage(pageQuery, measurementSearchParam);
            PageResult<FormatMeasurement> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatMeasurement> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(FormatMeasurement formatMeasurement) {
        if(checkNameExist(formatMeasurement.getName(),formatMeasurement.getId())) {
            throw new BusinessException(EmBusinessError.MEASUREMENT_NAME);
        }

        FormatMeasurement formatMeasurement1 = new FormatMeasurement();
        formatMeasurement1.setName(formatMeasurement.getName());
        formatMeasurement1.setOperator("操作人");
        formatMeasurement1.setOperatorIp("124.124.124");
        formatMeasurement1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatMeasurementMapper.insertSelective(formatMeasurement1);
    }
    @Override
    public void delete(int fqtId) {
        FormatMeasurement formatMeasurement = formatMeasurementMapper.selectByPrimaryKey(fqtId);
        if(formatMeasurement==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatMeasurementMapper.deleteByPrimaryKey(fqtId);
    }

    @Override
    @Transactional
    public void update(FormatMeasurement formatMeasurement) {
        if(formatMeasurementMapper.selectByPrimaryKey(formatMeasurement.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkNameExist(formatMeasurement.getName(),formatMeasurement.getId())) {
            throw new BusinessException(EmBusinessError.MEASUREMENT_NAME);
        }

        FormatMeasurement formatMeasurement1 = new FormatMeasurement();
        formatMeasurement1.setId(formatMeasurement.getId());
        formatMeasurement1.setName(formatMeasurement.getName());
        formatMeasurement1.setOperator("操作人");
        formatMeasurement1.setOperatorIp("124.124.124");
        formatMeasurement1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatMeasurementMapper.updateByPrimaryKeySelective(formatMeasurement1);
    }
    public boolean checkNameExist(String name,Integer id) {
        return formatMeasurementMapper.countByName(name, id) > 0;
    }
}
