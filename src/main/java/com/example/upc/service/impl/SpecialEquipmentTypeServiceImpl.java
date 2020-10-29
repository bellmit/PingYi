package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dao.SpecialEquipmentTypeOneMapper;
import com.example.upc.dao.SpecialEquipmentTypeThreeMapper;
import com.example.upc.dao.SpecialEquipmentTypeTwoMapper;
import com.example.upc.dataobject.SpecialEquipmentTypeOne;
import com.example.upc.dataobject.SpecialEquipmentTypeThree;
import com.example.upc.dataobject.SpecialEquipmentTypeTwo;
import com.example.upc.service.SpecialEquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SpecialEquipmentTypeServiceImpl implements SpecialEquipmentTypeService {
    @Autowired
    SpecialEquipmentTypeOneMapper specialEquipmentTypeOneMapper;
    @Autowired
    SpecialEquipmentTypeTwoMapper specialEquipmentTypeTwoMapper;
    @Autowired
    SpecialEquipmentTypeThreeMapper specialEquipmentTypeThreeMapper;
    @Autowired
    private ValidatorImpl validator;

    //类别1的一套接口实现
    @Override
    public PageResult getPageOne(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam) {
        int count=specialEquipmentTypeOneMapper.countList(measurementSearchParam);
        if (count > 0) {
            List<SpecialEquipmentTypeOne> fqtList = specialEquipmentTypeOneMapper.getPage(pageQuery, measurementSearchParam);
            PageResult<SpecialEquipmentTypeOne> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpecialEquipmentTypeOne> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insertOne(SpecialEquipmentTypeOne specialEquipmentTypeOne) {

        ValidationResult result = validator.validate(specialEquipmentTypeOne);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkAllExistOne(specialEquipmentTypeOne.getName(),specialEquipmentTypeOne.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        SpecialEquipmentTypeOne specialEquipmentTypeOne1 = new SpecialEquipmentTypeOne();
        specialEquipmentTypeOne1.setName(specialEquipmentTypeOne.getName());
        specialEquipmentTypeOne1.setOperator("操作人");
        specialEquipmentTypeOne1.setOperatorIp("124.124.124");
        specialEquipmentTypeOne1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentTypeOneMapper.insertSelective(specialEquipmentTypeOne1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }

    @Override
    public void deleteOne(int id) {
        SpecialEquipmentTypeOne specialEquipmentTypeOne = specialEquipmentTypeOneMapper.selectByPrimaryKey(id);
        if(specialEquipmentTypeOne==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        specialEquipmentTypeOneMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void updateOne(SpecialEquipmentTypeOne specialEquipmentTypeOne) {
        if(specialEquipmentTypeOneMapper.selectByPrimaryKey(specialEquipmentTypeOne.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkAllExistOne(specialEquipmentTypeOne.getName(),specialEquipmentTypeOne.getId())) {
            throw new BusinessException(EmBusinessError.MEASUREMENT_NAME);
        }

        SpecialEquipmentTypeOne specialEquipmentTypeOne1 = new SpecialEquipmentTypeOne();
        specialEquipmentTypeOne1.setId(specialEquipmentTypeOne.getId());
        specialEquipmentTypeOne1.setName(specialEquipmentTypeOne.getName());
        specialEquipmentTypeOne1.setOperator("操作人");
        specialEquipmentTypeOne1.setOperatorIp("124.124.124");
        specialEquipmentTypeOne1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentTypeOneMapper.updateByPrimaryKeySelective(specialEquipmentTypeOne1);
    }

    //类别2的一套接口实现
    @Override
    public PageResult getPageTwo(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam) {
        int count=specialEquipmentTypeTwoMapper.countList(measurementSearchParam);
        if (count > 0) {
            List<SpecialEquipmentTypeTwo> fqtList = specialEquipmentTypeTwoMapper.getPage(pageQuery, measurementSearchParam);
            PageResult<SpecialEquipmentTypeTwo> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpecialEquipmentTypeTwo> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageTwoByParent(PageQuery pageQuery, String parent, MeasurementSearchParam measurementSearchParam) {
        int count=specialEquipmentTypeTwoMapper.countListByParent(parent, measurementSearchParam);
        if (count > 0) {
            List<SpecialEquipmentTypeTwo> fqtList = specialEquipmentTypeTwoMapper.getPageByParent(pageQuery, parent, measurementSearchParam);
            PageResult<SpecialEquipmentTypeTwo> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpecialEquipmentTypeTwo> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insertTwo(SpecialEquipmentTypeTwo specialEquipmentTypeTwo) {

        ValidationResult result = validator.validate(specialEquipmentTypeTwo);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkAllExistTwo(specialEquipmentTypeTwo.getParentName(), specialEquipmentTypeTwo.getName(),specialEquipmentTypeTwo.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        SpecialEquipmentTypeTwo specialEquipmentTypeTwo1 = new SpecialEquipmentTypeTwo();
        specialEquipmentTypeTwo1.setParentName(specialEquipmentTypeTwo.getParentName());
        specialEquipmentTypeTwo1.setName(specialEquipmentTypeTwo.getName());
        specialEquipmentTypeTwo1.setOperator("操作人");
        specialEquipmentTypeTwo1.setOperatorIp("124.124.124");
        specialEquipmentTypeTwo1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentTypeTwoMapper.insertSelective(specialEquipmentTypeTwo1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }

    @Override
    public void deleteTwo(int id) {
        SpecialEquipmentTypeTwo specialEquipmentTypeTwo = specialEquipmentTypeTwoMapper.selectByPrimaryKey(id);
        if(specialEquipmentTypeTwo==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        specialEquipmentTypeTwoMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void updateTwo(SpecialEquipmentTypeTwo specialEquipmentTypeTwo) {
        if(specialEquipmentTypeTwoMapper.selectByPrimaryKey(specialEquipmentTypeTwo.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkAllExistTwo(specialEquipmentTypeTwo.getParentName(), specialEquipmentTypeTwo.getName(),specialEquipmentTypeTwo.getId())) {
            throw new BusinessException(EmBusinessError.MEASUREMENT_NAME);
        }

        SpecialEquipmentTypeTwo specialEquipmentTypeTwo1 = new SpecialEquipmentTypeTwo();
        specialEquipmentTypeTwo1.setId(specialEquipmentTypeTwo.getId());
        specialEquipmentTypeTwo1.setParentName(specialEquipmentTypeTwo.getParentName());
        specialEquipmentTypeTwo1.setName(specialEquipmentTypeTwo.getName());
        specialEquipmentTypeTwo1.setOperator("操作人");
        specialEquipmentTypeTwo1.setOperatorIp("124.124.124");
        specialEquipmentTypeTwo1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentTypeTwoMapper.updateByPrimaryKeySelective(specialEquipmentTypeTwo1);
    }

    //类别3各个接口
    @Override
    public PageResult getPageThree(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam) {
        int count=specialEquipmentTypeThreeMapper.countList(measurementSearchParam);
        if (count > 0) {
            List<SpecialEquipmentTypeThree> fqtList = specialEquipmentTypeThreeMapper.getPage(pageQuery, measurementSearchParam);
            PageResult<SpecialEquipmentTypeThree> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpecialEquipmentTypeThree> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageThreeByParent(PageQuery pageQuery, String parent, MeasurementSearchParam measurementSearchParam) {
        int count=specialEquipmentTypeThreeMapper.countListByParent(parent, measurementSearchParam);
        if (count > 0) {
            List<SpecialEquipmentTypeThree> fqtList = specialEquipmentTypeThreeMapper.getPageByParent(pageQuery, parent, measurementSearchParam);
            PageResult<SpecialEquipmentTypeThree> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpecialEquipmentTypeThree> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insertThree(SpecialEquipmentTypeThree specialEquipmentTypeThree) {

        ValidationResult result = validator.validate(specialEquipmentTypeThree);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(checkAllExistTwo(specialEquipmentTypeThree.getParentName(), specialEquipmentTypeThree.getName(),specialEquipmentTypeThree.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        SpecialEquipmentTypeThree specialEquipmentTypeThree1 = new SpecialEquipmentTypeThree();
        specialEquipmentTypeThree1.setParentName(specialEquipmentTypeThree.getParentName());
        specialEquipmentTypeThree1.setName(specialEquipmentTypeThree.getName());
        specialEquipmentTypeThree1.setOperator("操作人");
        specialEquipmentTypeThree1.setOperatorIp("124.124.124");
        specialEquipmentTypeThree1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentTypeThreeMapper.insertSelective(specialEquipmentTypeThree1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }

    @Override
    public void deleteThree(int id) {
        SpecialEquipmentTypeThree specialEquipmentTypeThree = specialEquipmentTypeThreeMapper.selectByPrimaryKey(id);
        if(specialEquipmentTypeThree==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        specialEquipmentTypeThreeMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void updateThree(SpecialEquipmentTypeThree specialEquipmentTypeThree) {
        if(specialEquipmentTypeThreeMapper.selectByPrimaryKey(specialEquipmentTypeThree.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        if(checkAllExistThree(specialEquipmentTypeThree.getParentName(), specialEquipmentTypeThree.getName(),specialEquipmentTypeThree.getId())) {
            throw new BusinessException(EmBusinessError.MEASUREMENT_NAME);
        }

        SpecialEquipmentTypeThree specialEquipmentTypeThree1 = new SpecialEquipmentTypeThree();
        specialEquipmentTypeThree1.setId(specialEquipmentTypeThree.getId());
        specialEquipmentTypeThree1.setParentName(specialEquipmentTypeThree.getParentName());
        specialEquipmentTypeThree1.setName(specialEquipmentTypeThree.getName());
        specialEquipmentTypeThree1.setOperator("操作人");
        specialEquipmentTypeThree1.setOperatorIp("124.124.124");
        specialEquipmentTypeThree1.setOperatorTime(new Date());

        // TODO: sendEmail

        specialEquipmentTypeThreeMapper.updateByPrimaryKeySelective(specialEquipmentTypeThree1);
    }

    public boolean checkAllExistOne(String name, Integer id) {
        return specialEquipmentTypeOneMapper.countByName(name, id) > 0;
    }

    public boolean checkAllExistTwo(String parent, String name, Integer id) {
        return specialEquipmentTypeTwoMapper.countByName(parent, name, id) > 0;
    }

    public boolean checkAllExistThree(String parent, String name, Integer id) {
        return specialEquipmentTypeTwoMapper.countByName(parent, name, id) > 0;
    }
}
