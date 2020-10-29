package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysMaterialDataMapper;
import com.example.upc.dataobject.SysMaterialData;
import com.example.upc.service.MaterialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MaterialDataServiceImpl implements MaterialDataService {
    @Autowired
    SysMaterialDataMapper sysMaterialDataMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=sysMaterialDataMapper.countList();
        if (count > 0) {
            List<SysMaterialData> spList = sysMaterialDataMapper.getPage(pageQuery);
            PageResult<SysMaterialData> pageResult = new PageResult<>();
            pageResult.setData(spList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysMaterialData> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SysMaterialData sysMaterialData) {
//        if(checkComNameExist(sysMaterialData.getComName(), sysMaterialData.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        SysMaterialData materialData = new SysMaterialData();
        materialData.setComName(sysMaterialData.getComName());
        materialData.setComType(sysMaterialData.getComType());
        materialData.setContactsName(sysMaterialData.getContactsName());
        materialData.setMaterialType(sysMaterialData.getMaterialType());
        materialData.setMaterialName(sysMaterialData.getMaterialName());
        materialData.setInTime(sysMaterialData.getInTime());
        materialData.setManufacturer(sysMaterialData.getManufacturer());
        materialData.setBrand(sysMaterialData.getBrand());
        materialData.setContent(sysMaterialData.getContent());
        materialData.setProduceTime(sysMaterialData.getProduceTime());
        materialData.setQualityTime(sysMaterialData.getQualityTime());
        materialData.setValidityTime(sysMaterialData.getValidityTime());
        materialData.setInNumber(sysMaterialData.getInNumber());
        materialData.setMeasurement(sysMaterialData.getMeasurement());
        materialData.setSupplier(sysMaterialData.getSupplier());
        materialData.setAcceptor(sysMaterialData.getAcceptor());
        materialData.setOperator("操作人");
        materialData.setOperatorIp("124.124.124");
        materialData.setOperatorTime(new Date());

        // TODO: sendEmail

        sysMaterialDataMapper.insertSelective(materialData);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        SysMaterialData sysMaterialData = sysMaterialDataMapper.selectByPrimaryKey(fpId);
        if(sysMaterialData==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        sysMaterialDataMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(SysMaterialData sysMaterialData) {
//        if(checkComNameExist(sysMaterialData.getComName(), sysMaterialData.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        SysMaterialData materialData = new SysMaterialData();
        materialData.setId(sysMaterialData.getId());
        materialData.setComName(sysMaterialData.getComName());
        materialData.setComType(sysMaterialData.getComType());
        materialData.setContactsName(sysMaterialData.getContactsName());
        materialData.setMaterialType(sysMaterialData.getMaterialType());
        materialData.setMaterialName(sysMaterialData.getMaterialName());
        materialData.setInTime(sysMaterialData.getInTime());
        materialData.setManufacturer(sysMaterialData.getManufacturer());
        materialData.setBrand(sysMaterialData.getBrand());
        materialData.setContent(sysMaterialData.getContent());
        materialData.setProduceTime(sysMaterialData.getProduceTime());
        materialData.setQualityTime(sysMaterialData.getQualityTime());
        materialData.setValidityTime(sysMaterialData.getValidityTime());
        materialData.setInNumber(sysMaterialData.getInNumber());
        materialData.setMeasurement(sysMaterialData.getMeasurement());
        materialData.setSupplier(sysMaterialData.getSupplier());
        materialData.setAcceptor(sysMaterialData.getAcceptor());
        materialData.setOperator("操作人");
        materialData.setOperatorIp("124.124.124");
        materialData.setOperatorTime(new Date());

        // TODO: sendEmail

        sysMaterialDataMapper.updateByPrimaryKey(materialData);
    }
//    public boolean checkComNameExist(String comName, Integer comId) {
//        return sysMaterialDataMapper.countByComName(comName, comId) > 0;
//    }
}
