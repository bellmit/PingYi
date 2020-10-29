package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.InspectBookParam;
import com.example.upc.controller.param.InspectDailyClauseParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.controller.searchParam.InspectSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.InspectDailyFood;
import com.example.upc.dataobject.SysArea;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.service.InspectDailyBookService;
import com.example.upc.service.InspectDailyClauseService;
import com.example.upc.service.InspectDailyFoodService;
import com.example.upc.service.model.InspectCheck;
import com.example.upc.service.model.InspectIndustryNumber;
import com.example.upc.service.model.InspectStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zcc
 * @date 2019/5/18 18:57
 */
@Service
public class InspectDailyFoodServiceImpl implements InspectDailyFoodService {
    @Autowired
    private InspectDailyFoodMapper inspectDailyFoodMapper;
    @Autowired
    private InspectDailyClauseService inspectDailyClauseService;
    @Autowired
    private InspectDailyCluseMapper inspectDailyCluseMapper;
    @Autowired
    private InspectDailyBookService inspectDailyBookService;
    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private ValidatorImpl validator;
    @Override
    public PageResult<InspectDailyFood> getPage(PageQuery pageQuery, int industry, List<Integer> areaList, InspectSearchParam searchParam) {
        int count=inspectDailyFoodMapper.countList(industry,areaList,searchParam);
        if (count > 0) {
            List<InspectDailyFood> inspectDailyFoodList = inspectDailyFoodMapper.getPage(pageQuery,industry,areaList,searchParam);
            PageResult<InspectDailyFood> pageResult = new PageResult<>();
            pageResult.setData(inspectDailyFoodList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectDailyFood> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public InspectDailyFood getInspectDailyFood(int checkId) {
        return inspectDailyFoodMapper.selectByPrimaryKey(checkId);
    }

    @Override
    @Transactional
    public void insert(InspectDailyFood inspectDailyFood,List<InspectDailyClauseParam> inspectDailyClauseParamList) {
        ValidationResult result = validator.validate(inspectDailyFood);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(inspectDailyClauseParamList==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"检查要点表为空");
        }
        inspectDailyFood.setOperatorIp("124.124.124");
        inspectDailyFood.setOperator("操作人");
        inspectDailyFood.setOperatorTime(new Date());
        inspectDailyFoodMapper.insertSelective(inspectDailyFood);
        inspectDailyClauseService.changeDailyClauseList(inspectDailyClauseParamList,inspectDailyFood.getId());
    }

    @Override
    @Transactional
    public void update(InspectDailyFood inspectDailyFood, List<InspectDailyClauseParam> inspectDailyClauseParamList) {
        ValidationResult result = validator.validate(inspectDailyFood);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        InspectDailyFood before = inspectDailyFoodMapper.selectByPrimaryKey(inspectDailyFood.getId());
        if(before == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新检查不存在");
        }
        if(inspectDailyClauseParamList==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"检查要点表为空");
        }
        inspectDailyFood.setOperatorIp("124.124.124");
        inspectDailyFood.setOperator("操作人");
        inspectDailyFood.setOperatorTime(new Date());
        inspectDailyFoodMapper.updateByPrimaryKeySelective(inspectDailyFood);
        inspectDailyClauseService.changeDailyClauseList(inspectDailyClauseParamList,inspectDailyFood.getId());
    }

    @Override
    @Transactional
    public void delete(int id) {
        InspectDailyFood before = inspectDailyFoodMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除信息不存在");
        }
        inspectDailyFoodMapper.deleteByPrimaryKey(id);
        inspectDailyCluseMapper.deleteByDailyId(id);
        inspectDailyBookService.deleteByDailyId(id);
    }

    @Override
    public String getCheckLastDate(int objectId) {
        InspectDailyFood inspectDailyFood = inspectDailyFoodMapper.getCheckLastDate(objectId);
        if (inspectDailyFood == null) {
            return "";
        }else{
            return inspectDailyFood.getCheckDate();
        }
    }

    @Override
    public int yearCheckNumber(int objectId) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");
        String str = format.format(date);
        return inspectDailyFoodMapper.yearCheckNumber(objectId,str);
    }

    @Override
    public Map<String, Object> getStatistics(String checkDate) {
        List<SysArea> areaList = sysAreaMapper.getAllAreaPa();
        Map<String,Object> map = new HashMap<>();
        Map<Integer,Object> areaCount= new HashMap<>();
        for(SysArea sysArea : areaList){
            InspectStatistics inspectStatistics =new InspectStatistics();
            InspectCheck inspectCheck1 = new InspectCheck();
            inspectCheck1.setHaveCheck(inspectDailyFoodMapper.countHaveCheckNumber(sysArea.getId(),2,checkDate));
            inspectCheck1.setShouldCheck(CheckShouldNumber(inspectDailyFoodMapper.countShouldCheckNumber(sysArea.getId(),2)));
            inspectCheck1.setCheckEnterprise(inspectDailyFoodMapper.countByCheckEnterprise(sysArea.getId(),2,checkDate));
            inspectCheck1.setNumberEnterprise(inspectDailyFoodMapper.countByEnterprise(sysArea.getId(),2));
            inspectStatistics.setFoodCommon(inspectCheck1);
            InspectCheck inspectCheck2 = new InspectCheck();
            inspectCheck2.setHaveCheck(inspectDailyFoodMapper.countHaveCheckNumber(sysArea.getId(),3,checkDate));
            inspectCheck2.setShouldCheck(CheckShouldNumber(inspectDailyFoodMapper.countShouldCheckNumber(sysArea.getId(),3)));
            inspectCheck2.setCheckEnterprise(inspectDailyFoodMapper.countByCheckEnterprise(sysArea.getId(),3,checkDate));
            inspectCheck2.setNumberEnterprise(inspectDailyFoodMapper.countByEnterprise(sysArea.getId(),3));
            inspectStatistics.setFoodCirculate(inspectCheck2);
            InspectCheck inspectCheck3 = new InspectCheck();
            inspectCheck3.setHaveCheck(inspectDailyFoodMapper.countHaveCheckNumber(sysArea.getId(),5,checkDate));
            inspectCheck3.setShouldCheck(CheckShouldNumber(inspectDailyFoodMapper.countShouldCheckNumber(sysArea.getId(),5)));
            inspectCheck3.setCheckEnterprise(inspectDailyFoodMapper.countByCheckEnterprise(sysArea.getId(),5,checkDate));
            inspectCheck3.setNumberEnterprise(inspectDailyFoodMapper.countByEnterprise(sysArea.getId(),5));
            inspectStatistics.setFoodProduce(inspectCheck3);
            areaCount.put(sysArea.getId(),inspectStatistics);
        }
        InspectIndustryNumber inspectIndustryNumberA = new InspectIndustryNumber();
        inspectIndustryNumberA.setFoodCommon(inspectDailyFoodMapper.countEnterpriseByAssessment("A",2));
        inspectIndustryNumberA.setFoodCirculate(inspectDailyFoodMapper.countEnterpriseByAssessment("A",3));
        inspectIndustryNumberA.setFoodProduce(inspectDailyFoodMapper.countEnterpriseByAssessment("A",5));
        map.put("enterpriseMarkA",inspectIndustryNumberA);
        InspectIndustryNumber inspectIndustryNumberB = new InspectIndustryNumber();
        inspectIndustryNumberB.setFoodCommon(inspectDailyFoodMapper.countEnterpriseByAssessment("B",2));
        inspectIndustryNumberB.setFoodCirculate(inspectDailyFoodMapper.countEnterpriseByAssessment("B",3));
        inspectIndustryNumberB.setFoodProduce(inspectDailyFoodMapper.countEnterpriseByAssessment("B",5));
        map.put("enterpriseMarkB",inspectIndustryNumberB);
        InspectIndustryNumber inspectIndustryNumberC = new InspectIndustryNumber();
        inspectIndustryNumberC.setFoodCommon(inspectDailyFoodMapper.countEnterpriseByAssessment("C",2));
        inspectIndustryNumberC.setFoodCirculate(inspectDailyFoodMapper.countEnterpriseByAssessment("C",3));
        inspectIndustryNumberC.setFoodProduce(inspectDailyFoodMapper.countEnterpriseByAssessment("C",5));
        map.put("enterpriseMarkC",inspectIndustryNumberC);
        InspectIndustryNumber inspectIndustryNumberD = new InspectIndustryNumber();
        inspectIndustryNumberD.setFoodCommon(inspectDailyFoodMapper.countEnterpriseByAssessment("D",2));
        inspectIndustryNumberD.setFoodCirculate(inspectDailyFoodMapper.countEnterpriseByAssessment("D",3));
        inspectIndustryNumberD.setFoodProduce(inspectDailyFoodMapper.countEnterpriseByAssessment("D",5));
        map.put("enterpriseMarkD",inspectIndustryNumberD);
        InspectIndustryNumber inspectIndustryNumber = new InspectIndustryNumber();
        inspectIndustryNumber.setFoodCommon(inspectDailyFoodMapper.countEnterpriseByAssessment("",2));
        inspectIndustryNumber.setFoodCirculate(inspectDailyFoodMapper.countEnterpriseByAssessment("",3));
        inspectIndustryNumber.setFoodProduce(inspectDailyFoodMapper.countEnterpriseByAssessment("",5));
        map.put("enterpriseMarkN",inspectIndustryNumber);
        map.put("areaList",areaList);
        map.put("areaCount",areaCount);
        return map;
    }
    private int CheckShouldNumber (Integer shouldNumber){
        return shouldNumber==null?0:shouldNumber;
    }
}
