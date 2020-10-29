package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.FoodSupervisionMapper;
import com.example.upc.dataobject.FoodSupervision;
import com.example.upc.service.FoodSupervisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodSupervisionServiceImpl implements FoodSupervisionService {
    @Autowired
    FoodSupervisionMapper foodSupervisionMapper;
    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=foodSupervisionMapper.countList();
        if (count > 0) {
            List<FoodSupervision> fpList = foodSupervisionMapper.getPage(pageQuery);
            PageResult<FoodSupervision> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<FoodSupervision> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(FoodSupervision foodSupervision) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        FoodSupervision foodSupervision1 = new FoodSupervision();
        foodSupervision1.setCheckType(foodSupervision.getCheckType());
        foodSupervision1.setCheckObject(foodSupervision.getCheckObject());
        foodSupervision1.setRegion(foodSupervision.getRegion());
        foodSupervision1.setGrid(foodSupervision.getGrid());
        foodSupervision1.setCheckAddress(foodSupervision.getCheckAddress());
        foodSupervision1.setOkNumber(foodSupervision.getOkNumber());
        foodSupervision1.setChargePerson(foodSupervision.getChargePerson());
        foodSupervision1.setCheckOrgan(foodSupervision.getCheckOrgan());
        foodSupervision1.setContactPhone(foodSupervision.getContactPhone());
        foodSupervision1.setEntourage(foodSupervision.getEntourage());
        foodSupervision1.setSupervisor(foodSupervision.getSupervisor());
        foodSupervision1.setSupervisorNumber(foodSupervision.getSupervisorNumber());
        foodSupervision1.setCheckDate(foodSupervision.getCheckDate());
        foodSupervision1.setCheckStartHour(foodSupervision.getCheckStartHour());
        foodSupervision1.setCheckStartMinute(foodSupervision.getCheckStartMinute());
        foodSupervision1.setCheckEndHour(foodSupervision.getCheckEndHour());
        foodSupervision1.setCheckEndMinute(foodSupervision.getCheckEndMinute());
        foodSupervision1.setLastCheckTime(foodSupervision.getLastCheckTime());
        foodSupervision1.setCheckCount(foodSupervision.getCheckCount());
        foodSupervision1.setCheckFrequence(foodSupervision.getCheckFrequence());
        foodSupervision1.setCheckTotal(foodSupervision.getCheckTotal());
        foodSupervision1.setNo1(foodSupervision.getNo1());
        foodSupervision1.setNo2(foodSupervision.getNo2());
        foodSupervision1.setNo3(foodSupervision.getNo3());
        foodSupervision1.setNo4(foodSupervision.getNo4());
        foodSupervision1.setNo5(foodSupervision.getNo5());
        foodSupervision1.setNo6(foodSupervision.getNo6());
        foodSupervision1.setNo7(foodSupervision.getNo7());
        foodSupervision1.setNo8(foodSupervision.getNo8());
        foodSupervision1.setNo9(foodSupervision.getNo9());
        foodSupervision1.setNo10(foodSupervision.getNo10());
        foodSupervision1.setNo11(foodSupervision.getNo11());
        foodSupervision1.setNo12(foodSupervision.getNo12());
        foodSupervision1.setNo13(foodSupervision.getNo13());
        foodSupervision1.setNo14(foodSupervision.getNo14());
        foodSupervision1.setNo15(foodSupervision.getNo15());
        foodSupervision1.setNo16(foodSupervision.getNo16());
        foodSupervision1.setNo17(foodSupervision.getNo17());
        foodSupervision1.setNo18(foodSupervision.getNo18());
        foodSupervision1.setNo19(foodSupervision.getNo19());
        foodSupervision1.setNo20(foodSupervision.getNo20());
        foodSupervision1.setNo21(foodSupervision.getNo21());
        foodSupervision1.setNo22(foodSupervision.getNo22());
        foodSupervision1.setNo23(foodSupervision.getNo23());
        foodSupervision1.setNo24(foodSupervision.getNo24());
        foodSupervision1.setNo25(foodSupervision.getNo25());
        foodSupervision1.setNo26(foodSupervision.getNo26());
        foodSupervision1.setNo27(foodSupervision.getNo27());
        foodSupervision1.setNo28(foodSupervision.getNo28());
        foodSupervision1.setNo29(foodSupervision.getNo29());
        foodSupervision1.setNo30(foodSupervision.getNo30());
        foodSupervision1.setCheckResult(foodSupervision.getCheckResult());
        foodSupervision1.setResultProcess(foodSupervision.getResultProcess());
        foodSupervision1.setProblem(foodSupervision.getProblem());
        foodSupervision1.setDisposalMeasures(foodSupervision.getDisposalMeasures());
        foodSupervision1.setDocument(foodSupervision.getDocument());
        // TODO: sendEmail

        foodSupervisionMapper.insertSelective(foodSupervision1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        FoodSupervision foodSupervision = foodSupervisionMapper.selectByPrimaryKey(fpId);
        if(foodSupervision==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        foodSupervisionMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(FoodSupervision foodSupervision) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        FoodSupervision foodSupervision1 = new FoodSupervision();
        foodSupervision1.setId(foodSupervision.getId());
        foodSupervision1.setCheckType(foodSupervision.getCheckType());
        foodSupervision1.setCheckObject(foodSupervision.getCheckObject());
        foodSupervision1.setRegion(foodSupervision.getRegion());
        foodSupervision1.setGrid(foodSupervision.getGrid());
        foodSupervision1.setCheckAddress(foodSupervision.getCheckAddress());
        foodSupervision1.setOkNumber(foodSupervision.getOkNumber());
        foodSupervision1.setChargePerson(foodSupervision.getChargePerson());
        foodSupervision1.setCheckOrgan(foodSupervision.getCheckOrgan());
        foodSupervision1.setContactPhone(foodSupervision.getContactPhone());
        foodSupervision1.setEntourage(foodSupervision.getEntourage());
        foodSupervision1.setSupervisor(foodSupervision.getSupervisor());
        foodSupervision1.setSupervisorNumber(foodSupervision.getSupervisorNumber());
        foodSupervision1.setCheckDate(foodSupervision.getCheckDate());
        foodSupervision1.setCheckStartHour(foodSupervision.getCheckStartHour());
        foodSupervision1.setCheckStartMinute(foodSupervision.getCheckStartMinute());
        foodSupervision1.setCheckEndHour(foodSupervision.getCheckEndHour());
        foodSupervision1.setCheckEndMinute(foodSupervision.getCheckEndMinute());
        foodSupervision1.setLastCheckTime(foodSupervision.getLastCheckTime());
        foodSupervision1.setCheckCount(foodSupervision.getCheckCount());
        foodSupervision1.setCheckFrequence(foodSupervision.getCheckFrequence());
        foodSupervision1.setCheckTotal(foodSupervision.getCheckTotal());
        foodSupervision1.setNo1(foodSupervision.getNo1());
        foodSupervision1.setNo2(foodSupervision.getNo2());
        foodSupervision1.setNo3(foodSupervision.getNo3());
        foodSupervision1.setNo4(foodSupervision.getNo4());
        foodSupervision1.setNo5(foodSupervision.getNo5());
        foodSupervision1.setNo6(foodSupervision.getNo6());
        foodSupervision1.setNo7(foodSupervision.getNo7());
        foodSupervision1.setNo8(foodSupervision.getNo8());
        foodSupervision1.setNo9(foodSupervision.getNo9());
        foodSupervision1.setNo10(foodSupervision.getNo10());
        foodSupervision1.setNo11(foodSupervision.getNo11());
        foodSupervision1.setNo12(foodSupervision.getNo12());
        foodSupervision1.setNo13(foodSupervision.getNo13());
        foodSupervision1.setNo14(foodSupervision.getNo14());
        foodSupervision1.setNo15(foodSupervision.getNo15());
        foodSupervision1.setNo16(foodSupervision.getNo16());
        foodSupervision1.setNo17(foodSupervision.getNo17());
        foodSupervision1.setNo18(foodSupervision.getNo18());
        foodSupervision1.setNo19(foodSupervision.getNo19());
        foodSupervision1.setNo20(foodSupervision.getNo20());
        foodSupervision1.setNo21(foodSupervision.getNo21());
        foodSupervision1.setNo22(foodSupervision.getNo22());
        foodSupervision1.setNo23(foodSupervision.getNo23());
        foodSupervision1.setNo24(foodSupervision.getNo24());
        foodSupervision1.setNo25(foodSupervision.getNo25());
        foodSupervision1.setNo26(foodSupervision.getNo26());
        foodSupervision1.setNo27(foodSupervision.getNo27());
        foodSupervision1.setNo28(foodSupervision.getNo28());
        foodSupervision1.setNo29(foodSupervision.getNo29());
        foodSupervision1.setNo30(foodSupervision.getNo30());
        foodSupervision1.setCheckResult(foodSupervision.getCheckResult());
        foodSupervision1.setResultProcess(foodSupervision.getResultProcess());
        foodSupervision1.setProblem(foodSupervision.getProblem());
        foodSupervision1.setDisposalMeasures(foodSupervision.getDisposalMeasures());
        foodSupervision1.setDocument(foodSupervision.getDocument());

        // TODO: sendEmail

        foodSupervisionMapper.updateByPrimaryKeySelective(foodSupervision1);
    }

}
