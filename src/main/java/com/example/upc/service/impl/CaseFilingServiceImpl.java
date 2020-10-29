package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.CaseFilingMapper;
import com.example.upc.dao.HikKeyMapper;
import com.example.upc.dataobject.CaseFiling;
import com.example.upc.dataobject.HikKey;
import com.example.upc.service.CaseFilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseFilingServiceImpl implements CaseFilingService {
    @Autowired
    CaseFilingMapper caseFilingMapper;
    @Autowired
    HikKeyMapper hikKeyMapper;

    @Override
    public HikKey selectTopOne(){
        HikKey hikKey= hikKeyMapper.selectTopOne();
        return hikKey;
    }


    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=caseFilingMapper.countList();
        if (count > 0) {
            List<CaseFiling> fpList = caseFilingMapper.getPage(pageQuery);
            PageResult<CaseFiling> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<CaseFiling> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(CaseFiling caseFiling) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        CaseFiling caseFiling1 = new CaseFiling();
        caseFiling1.setRecordNumber(caseFiling.getRecordNumber());
        caseFiling1.setRecordPerson(caseFiling.getRecordPerson());
        caseFiling1.setRecordDate(caseFiling.getRecordDate());
        caseFiling1.setRecordToType(caseFiling.getRecordToType());
        caseFiling1.setPartyName(caseFiling.getPartyName());
        caseFiling1.setPartyPerson(caseFiling.getPartyPerson());
        caseFiling1.setRegion(caseFiling.getRegion());
        caseFiling1.setAddress(caseFiling.getAddress());
        caseFiling1.setZipCode(caseFiling.getZipCode());
        caseFiling1.setLicenceSmallType(caseFiling.getLicenceSmallType());
        caseFiling1.setPunishmentTime(caseFiling.getPunishmentTime());
        caseFiling1.setPunishmentBookNumber(caseFiling.getPunishmentBookNumber());
        caseFiling1.setIdNumber(caseFiling.getIdNumber());
        caseFiling1.setLegalPerson(caseFiling.getLegalPerson());
        caseFiling1.setPhone(caseFiling.getPhone());
        caseFiling1.setBaseInformation(caseFiling.getBaseInformation());
        caseFiling1.setChargePerson(caseFiling.getChargePerson());
        caseFiling1.setChargeDate(caseFiling.getChargeDate());
        caseFiling1.setFilingDate(caseFiling.getFilingDate());
        caseFiling1.setReason(caseFiling.getReason());
        caseFiling1.setPunishmentType(caseFiling.getPunishmentType());
        caseFiling1.setPunishmentReason(caseFiling.getPunishmentReason());
        caseFiling1.setMajorIllegalFacts(caseFiling.getMajorIllegalFacts());
        caseFiling1.setPunishmentResult(caseFiling.getPunishmentResult());
        caseFiling1.setPunishmentDoType(caseFiling.getPunishmentDoType());
        caseFiling1.setHelpWay(caseFiling.getHelpWay());
        caseFiling1.setProductsControl(caseFiling.getProductsControl());
        caseFiling1.setPoliceWhere(caseFiling.getPoliceWhere());
        caseFiling1.setIsIn(caseFiling.getIsIn());
        caseFiling1.setPunishmentWhere(caseFiling.getPunishmentWhere());
        caseFiling1.setDoSuggestion(caseFiling.getDoSuggestion());
        caseFiling1.setDocument(caseFiling.getDocument());

        // TODO: sendEmail

        caseFilingMapper.insertSelective(caseFiling1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        CaseFiling caseFiling = caseFilingMapper.selectByPrimaryKey(fpId);
        if(caseFiling==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        caseFilingMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(CaseFiling caseFiling) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        CaseFiling caseFiling1 = new CaseFiling();
        caseFiling1.setId(caseFiling.getId());
        caseFiling1.setRecordNumber(caseFiling.getRecordNumber());
        caseFiling1.setRecordPerson(caseFiling.getRecordPerson());
        caseFiling1.setRecordDate(caseFiling.getRecordDate());
        caseFiling1.setRecordToType(caseFiling.getRecordToType());
        caseFiling1.setPartyName(caseFiling.getPartyName());
        caseFiling1.setPartyPerson(caseFiling.getPartyPerson());
        caseFiling1.setRegion(caseFiling.getRegion());
        caseFiling1.setAddress(caseFiling.getAddress());
        caseFiling1.setZipCode(caseFiling.getZipCode());
        caseFiling1.setLicenceSmallType(caseFiling.getLicenceSmallType());
        caseFiling1.setPunishmentTime(caseFiling.getPunishmentTime());
        caseFiling1.setPunishmentBookNumber(caseFiling.getPunishmentBookNumber());
        caseFiling1.setIdNumber(caseFiling.getIdNumber());
        caseFiling1.setLegalPerson(caseFiling.getLegalPerson());
        caseFiling1.setPhone(caseFiling.getPhone());
        caseFiling1.setBaseInformation(caseFiling.getBaseInformation());
        caseFiling1.setChargePerson(caseFiling.getChargePerson());
        caseFiling1.setChargeDate(caseFiling.getChargeDate());
        caseFiling1.setFilingDate(caseFiling.getFilingDate());
        caseFiling1.setReason(caseFiling.getReason());
        caseFiling1.setPunishmentType(caseFiling.getPunishmentType());
        caseFiling1.setPunishmentReason(caseFiling.getPunishmentReason());
        caseFiling1.setMajorIllegalFacts(caseFiling.getMajorIllegalFacts());
        caseFiling1.setPunishmentResult(caseFiling.getPunishmentResult());
        caseFiling1.setPunishmentDoType(caseFiling.getPunishmentDoType());
        caseFiling1.setHelpWay(caseFiling.getHelpWay());
        caseFiling1.setProductsControl(caseFiling.getProductsControl());
        caseFiling1.setPoliceWhere(caseFiling.getPoliceWhere());
        caseFiling1.setIsIn(caseFiling.getIsIn());
        caseFiling1.setPunishmentWhere(caseFiling.getPunishmentWhere());
        caseFiling1.setDoSuggestion(caseFiling.getDoSuggestion());
        caseFiling1.setDocument(caseFiling.getDocument());

        // TODO: sendEmail

        caseFilingMapper.updateByPrimaryKeySelective(caseFiling1);
    }
}
