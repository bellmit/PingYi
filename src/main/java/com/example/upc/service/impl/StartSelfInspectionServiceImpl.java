package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.controller.searchParam.StartSelfInspectionPublicSearchParam;
import com.example.upc.dao.InspectionPositionMapper;
import com.example.upc.dao.StartSelfInspectionMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.StartSelfInspectionService;
import com.example.upc.util.JsonToImageUrl;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 董志涵
 */
@Service
public class StartSelfInspectionServiceImpl implements StartSelfInspectionService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private StartSelfInspectionMapper startSelfInspectionMapper;
    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private InspectionPositionMapper inspectionPositionMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void insert(InspectionList inspectionList, SysUser sysUser){
        List<StartSelfInspection> startSelfInspection = inspectionList.getStartSelfInspectionList();
        String inspectionPositionName = inspectionList.getInspectionPosition();
//        ValidationResult result = validator.validate(startSelfInspection);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        Date inspectionDate = startSelfInspection.get(0).getInspectTime();
        String inspector = startSelfInspection.get(0).getInspector();
        InspectionPosition inspectionPosition = new InspectionPosition();
        inspectionPosition.setEnterprise(sysUser.getInfoId());
        inspectionPosition.setInspectionTime(inspectionDate);
        inspectionPosition.setInspector(inspector);
        inspectionPosition.setInspectionPositionName(inspectionPositionName);
        inspectionPositionMapper.insertSelective(inspectionPosition);

        for (StartSelfInspection item:startSelfInspection
        ) {
            item.setEnterprise(sysUser.getInfoId());
            item.setInspectionPosition(inspectionPosition.getId());
            item.setOperator(sysUser.getUsername());
            item.setOperatorIp("124.124.124");
            startSelfInspectionMapper.insertSelective(item);
        }

    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void update(InspectionList inspectionList ,SysUser sysUser){
        List<StartSelfInspection> startSelfInspection = inspectionList.getStartSelfInspectionList();
        String inspectionPositionName = inspectionList.getInspectionPosition();
//        ValidationResult result = validator.validate(startSelfInspection);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        Date inspectionDate = startSelfInspection.get(0).getInspectTime();
        String inspector = startSelfInspection.get(0).getInspector();
        InspectionPosition inspectionPosition = new InspectionPosition();
        inspectionPosition.setId(inspectionList.getInspectionPositionId());
        inspectionPosition.setEnterprise(sysUser.getInfoId());
        inspectionPosition.setInspectionTime(inspectionDate);
        inspectionPosition.setInspector(inspector);
        inspectionPosition.setInspectionPositionName(inspectionPositionName);
        inspectionPositionMapper.updateByPrimaryKeySelective(inspectionPosition);

        for (StartSelfInspection item:startSelfInspection
        ) {
            item.setEnterprise(sysUser.getInfoId());
            item.setInspectionPosition(inspectionPosition.getId());
            item.setOperator(sysUser.getUsername());
            item.setOperatorIp("124.124.124");
            startSelfInspectionMapper.updateByPrimaryKeySelective(item);
        }
    }

    @Override
    public void delete(int id){
        StartSelfInspection startSelfInspection = startSelfInspectionMapper.selectByPrimaryKey(id);
        if(startSelfInspection==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        inspectionPositionMapper.deleteByPrimaryKey(id);
        startSelfInspectionMapper.deleteByPosition(id);
    }

    @Override
    public List<StartSelfInspection> getByEnterpriseId (InspectionSearchParam inspectionSearchParam, int id){
        return startSelfInspectionMapper.getByEnterpriseId(inspectionSearchParam, id);
    }

    @Override
    public List<InspectionPosition> getInspectionPositionByDate (InspectionSearchParam inspectionSearchParam, int id){
        return inspectionPositionMapper.getInspectionPositionByDate(inspectionSearchParam,id);
    }

    @Override
    public List<StartSelfInspection> getInspectionByPosition(int positionId) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<StartSelfInspection> startSelfInspectionList = new ArrayList<>();
        startSelfInspectionList = startSelfInspectionMapper.getInspectionByPosition(positionId);
        for (StartSelfInspection item:startSelfInspectionList
        ) {
            item.setPicture(item.getPicture().equals("")?"": JsonToImageUrl.JSON2ImageUrl(item.getPicture()));
            item.setInspectTime(dateFormat.parse(dateFormat.format(item.getInspectTime())));
        }
        System.out.println(startSelfInspectionList.size());
        return startSelfInspectionList;
    }

    @Override
    public List<StartSelfInspectionPublicSearchParam> getInspectionByPositionPublic(InspectionSearchParam inspectionSearchParam, Integer id) throws ParseException {
        List<InspectionPosition> inspectionPositionList=inspectionPositionMapper.getInspectionPositionByDate(inspectionSearchParam,id);
        List<StartSelfInspectionPublicSearchParam> publicList = new ArrayList<>();

        for(InspectionPosition inspectionPosition:inspectionPositionList){
            StartSelfInspectionPublicSearchParam itemPublic =new StartSelfInspectionPublicSearchParam();
            BeanUtils.copyProperties(inspectionPosition,itemPublic);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<StartSelfInspection> startSelfInspectionList = new ArrayList<>();
            startSelfInspectionList = startSelfInspectionMapper.getInspectionByPosition(inspectionPosition.getId());
            List<StartSelfInspection> stringList = Lists.newArrayList();
            for (StartSelfInspection item:startSelfInspectionList
            ) {
                item.setPicture(item.getPicture().equals("")?"": JsonToImageUrl.JSON2ImageUrl(item.getPicture()));
                item.setInspectTime(dateFormat.parse(dateFormat.format(item.getInspectTime())));
                stringList.add(item);
            }
            itemPublic.setPicList(stringList);
            publicList.add(itemPublic);
        }
        return publicList;
    }
}
