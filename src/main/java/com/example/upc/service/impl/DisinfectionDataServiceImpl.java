package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysDisinfectionDataMapper;
import com.example.upc.dataobject.SysDisinfectionData;
import com.example.upc.service.DisinfectionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DisinfectionDataServiceImpl implements DisinfectionDataService {
    @Autowired
    SysDisinfectionDataMapper sysDisinfectionDataMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=sysDisinfectionDataMapper.countList();
        if (count > 0) {
            List<SysDisinfectionData> ddList = sysDisinfectionDataMapper.getPage(pageQuery);
            PageResult<SysDisinfectionData> pageResult = new PageResult<>();
            pageResult.setData(ddList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SysDisinfectionData> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(SysDisinfectionData sysDisinfectionData) {
//        if(checkComNameExist(sysDisinfectionData.getComName(), sysDisinfectionData.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        SysDisinfectionData disinfectionData = new SysDisinfectionData();
        disinfectionData.setComName(sysDisinfectionData.getComName());
        disinfectionData.setType(sysDisinfectionData.getType());
        disinfectionData.setDisinfectionTime(sysDisinfectionData.getDisinfectionTime());
        disinfectionData.setTablewareType(sysDisinfectionData.getTablewareType());
        disinfectionData.setTablewareNumber(sysDisinfectionData.getTablewareNumber());
        disinfectionData.setDisinfectionType(sysDisinfectionData.getDisinfectionType());
        disinfectionData.setStartTime(sysDisinfectionData.getStartTime());
        disinfectionData.setFinishTime(sysDisinfectionData.getFinishTime());
        disinfectionData.setIsDisinfection(sysDisinfectionData.getIsDisinfection());
        disinfectionData.setOperateName(sysDisinfectionData.getOperateName());
        disinfectionData.setOthers(sysDisinfectionData.getOthers());
        disinfectionData.setOperator("操作人");
        disinfectionData.setOperatorIp("124.124.124");
        disinfectionData.setOperatorTime(new Date());

        // TODO: sendEmail

        sysDisinfectionDataMapper.insertSelective(disinfectionData);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int ddId) {
        SysDisinfectionData sysDisinfectionData = sysDisinfectionDataMapper.selectByPrimaryKey(ddId);
        if(sysDisinfectionData==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        sysDisinfectionDataMapper.deleteByPrimaryKey(ddId);
    }
    @Override
    public void update(SysDisinfectionData sysDisinfectionData) {
//        if(checkComNameExist(sysDisinfectionData.getComName(), sysDisinfectionData.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        SysDisinfectionData disinfectionData = new SysDisinfectionData();
        disinfectionData.setId(sysDisinfectionData.getId());
        disinfectionData.setComName(sysDisinfectionData.getComName());
        disinfectionData.setType(sysDisinfectionData.getType());
        disinfectionData.setDisinfectionTime(sysDisinfectionData.getDisinfectionTime());
        disinfectionData.setTablewareType(sysDisinfectionData.getTablewareType());
        disinfectionData.setTablewareNumber(sysDisinfectionData.getTablewareNumber());
        disinfectionData.setDisinfectionType(sysDisinfectionData.getDisinfectionType());
        disinfectionData.setStartTime(sysDisinfectionData.getStartTime());
        disinfectionData.setFinishTime(sysDisinfectionData.getFinishTime());
        disinfectionData.setIsDisinfection(sysDisinfectionData.getIsDisinfection());
        disinfectionData.setOperateName(sysDisinfectionData.getOperateName());
        disinfectionData.setOthers(sysDisinfectionData.getOthers());
        disinfectionData.setOperator("操作人");
        disinfectionData.setOperatorIp("124.124.124");
        disinfectionData.setOperatorTime(new Date());

        // TODO: sendEmail

        sysDisinfectionDataMapper.updateByPrimaryKey(disinfectionData);
    }
//    public boolean checkComNameExist(String comName, Integer comId) {
//        return sysDisinfectionDataMapper.countByComName(comName, comId) > 0;
//    }
}
