package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.EnterpriseRegulatorSearchParam;
import com.example.upc.controller.searchParam.SpotCheckBaseSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.SpotCheckBaseService;
import com.example.upc.util.ExcalUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpotCheckBaseServiceImpl implements SpotCheckBaseService {
    @Autowired
    SpotCheckBaseMapper spotCheckBaseMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SupervisionGaMapper supervisionGaMapper;
    @Autowired
    SpotCheckEnterpriseMapper spotCheckEnterpriseMapper;
    @Autowired
    ViewSpotCheckEnterpriseTeamResultMapper viewSpotCheckEnterpriseTeamResultMapper;

    //政府看
    @Override
    public PageResult getPage(PageQuery pageQuery, int id, SpotCheckBaseSearchParam spotCheckBaseSearchParam) {
        SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(id);
        if (supervisionGa == null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERERROR);
        }
        int count=spotCheckBaseMapper.countListSup(supervisionGa.getUnitName(), spotCheckBaseSearchParam);
        if (count > 0) {
            List<SpotCheckBase> fpList = spotCheckBaseMapper.getPageSup(pageQuery, supervisionGa.getUnitName(), spotCheckBaseSearchParam);
            PageResult<SpotCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }
    //我看我自己
    @Override
    public PageResult getUser(PageQuery pageQuery, int id, SpotCheckBaseSearchParam spotCheckBaseSearchParam) {
        SpotCheckEnterprise spotCheckEnterprise = spotCheckEnterpriseMapper.getUser(id);
        if (spotCheckEnterprise == null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERNO);
        }
        int count=spotCheckBaseMapper.countListEnterprise(spotCheckEnterprise.getEnterpriseName(), spotCheckBaseSearchParam);
        if (count > 0) {
            List<SpotCheckBase> fpList = spotCheckBaseMapper.getPageEnterprise(pageQuery, spotCheckEnterprise.getEnterpriseName(), spotCheckBaseSearchParam);
            PageResult<SpotCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }

    //政府看
    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, SpotCheckBaseSearchParam spotCheckBaseSearchParam) {
        int count=spotCheckBaseMapper.countListAdmin(spotCheckBaseSearchParam);
        if (count > 0) {
            List<SpotCheckBase> fpList = spotCheckBaseMapper.getPageAdmin(pageQuery, spotCheckBaseSearchParam);
            PageResult<SpotCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageFoodType(PageQuery pageQuery, String type) {

        int count=spotCheckBaseMapper.countListFoodType(type);
        if (count > 0) {
            List<SpotCheckBase> fpList = spotCheckBaseMapper.getPageFoodType(pageQuery, type);
            PageResult<SpotCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageByEnterprise(PageQuery pageQuery, String name) {

        int count=spotCheckBaseMapper.countListByEnterprise(name);
        if (count > 0) {
            List<SpotCheckBase> fpList = spotCheckBaseMapper.getPageByEnterprise(pageQuery, name);
            PageResult<SpotCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SpotCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterpriseTeam(PageQuery pageQuery, EnterpriseRegulatorSearchParam enterpriseRegulatorSearchParam) {

        int count=viewSpotCheckEnterpriseTeamResultMapper.countList(enterpriseRegulatorSearchParam);
        if (count > 0) {
            List<ViewSpotCheckEnterpriseTeamResult> fpList = viewSpotCheckEnterpriseTeamResultMapper.getPage(pageQuery, enterpriseRegulatorSearchParam);
            PageResult<ViewSpotCheckEnterpriseTeamResult> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewSpotCheckEnterpriseTeamResult> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SpotCheckBase spotCheckBase, SysUser sysUser) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        SpotCheckBase spotCheckBase1 = new SpotCheckBase();
        spotCheckBase1.setReportNo(spotCheckBase.getReportNo());
        spotCheckBase1.setReportDate(spotCheckBase.getReportDate());
        spotCheckBase1.setCheckDate(spotCheckBase.getCheckDate());
        spotCheckBase1.setCheckType(spotCheckBase.getCheckType());

        if (sysUser.getUserType()==6)
        {
            spotCheckBase1.setCheckCompany("预留学校表单位名称");
            spotCheckBase1.setAddress("预留学校表地址");
        }
        else
        {
            spotCheckBase1.setCheckCompany(spotCheckBase.getCheckCompany());
            spotCheckBase1.setAddress(spotCheckBase.getAddress());
        }

        spotCheckBase1.setContact(spotCheckBase.getContact());
        spotCheckBase1.setPhone(spotCheckBase.getPhone());

        if (sysUser.getUserType()==5)
        {
            SpotCheckEnterprise spotCheckEnterprise = spotCheckEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
            spotCheckBase1.setCheckOrginization(spotCheckEnterprise.getEnterpriseName());
        }
        else
        {
            spotCheckBase1.setCheckOrginization(spotCheckBase.getCheckOrginization());
        }

        spotCheckBase1.setCheckStep(spotCheckBase.getCheckStep());
        spotCheckBase1.setCheckBasement(spotCheckBase.getCheckBasement());
        spotCheckBase1.setCheckCompanyType(spotCheckBase.getCheckCompanyType());
        spotCheckBase1.setBuyMoney(spotCheckBase.getBuyMoney());
        spotCheckBase1.setSampleName(spotCheckBase.getSampleName());
        spotCheckBase1.setSamplePerson(spotCheckBase.getSamplePerson());
        spotCheckBase1.setCheckResult(spotCheckBase.getCheckResult());
        spotCheckBase1.setResultItem(spotCheckBase.getResultItem());
        spotCheckBase1.setSpecifications(spotCheckBase.getSpecifications());
        spotCheckBase1.setBatch(spotCheckBase.getBatch());
        spotCheckBase1.setCheckMoney(spotCheckBase.getCheckMoney());
        spotCheckBase1.setSampleMoney(spotCheckBase.getSampleMoney());

        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(sysUser.getInfoId());
            spotCheckBase1.setTeam(supervisionGa.getUnitName());
            spotCheckBase1.setState("已上报");
        }
        else
        {
            spotCheckBase1.setTeam(spotCheckBase.getTeam());
            spotCheckBase1.setState("未上报");
        }

        spotCheckBase1.setSupervisor(spotCheckBase.getSupervisor());
        spotCheckBase1.setSiginal(spotCheckBase.getSiginal());
        spotCheckBase1.setDateType(spotCheckBase.getDateType());
        spotCheckBase1.setDate(spotCheckBase.getDate());
        spotCheckBase1.setSampleNo(spotCheckBase.getSampleNo());
        spotCheckBase1.setDay(spotCheckBase.getDay());
        spotCheckBase1.setStandard(spotCheckBase.getStandard());
        spotCheckBase1.setPromissionNo(spotCheckBase.getPromissionNo());
        spotCheckBase1.setProduceName(spotCheckBase.getProduceName());
        spotCheckBase1.setProduceAddress(spotCheckBase.getProduceAddress());
        spotCheckBase1.setProduceContact(spotCheckBase.getProduceContact());
        spotCheckBase1.setProducePhone(spotCheckBase.getProducePhone());
        spotCheckBase1.setSpotCheckNo(spotCheckBase.getSpotCheckNo());
        spotCheckBase1.setFoodType(spotCheckBase.getFoodType());
        spotCheckBase1.setDisposalType(spotCheckBase.getDisposalType());
        spotCheckBase1.setRemarke(spotCheckBase.getRemarke());
        spotCheckBase1.setDocument(spotCheckBase.getDocument());
        spotCheckBase1.setOperator("操作人");
        spotCheckBase1.setOperatorIp("124.124.124");
        spotCheckBase1.setOperatorTime(new Date());

        //TODO: sendEmail

        spotCheckBaseMapper.insertSelective(spotCheckBase1);
    }

    @Override
    @Transactional
    public void update(SpotCheckBase spotCheckBase) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        SpotCheckBase before = spotCheckBaseMapper.selectByPrimaryKey(spotCheckBase.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新企业不存在");
        }

        SpotCheckBase spotCheckBase1 = new SpotCheckBase();
        spotCheckBase1.setId(spotCheckBase.getId());
        spotCheckBase1.setReportNo(spotCheckBase.getReportNo());
        spotCheckBase1.setReportDate(spotCheckBase.getReportDate());
        spotCheckBase1.setCheckDate(spotCheckBase.getCheckDate());
        spotCheckBase1.setCheckType(spotCheckBase.getCheckType());
        spotCheckBase1.setCheckCompany(spotCheckBase.getCheckCompany());
        spotCheckBase1.setAddress(spotCheckBase.getAddress());
        spotCheckBase1.setContact(spotCheckBase.getContact());
        spotCheckBase1.setPhone(spotCheckBase.getPhone());
        spotCheckBase1.setCheckOrginization(spotCheckBase.getCheckOrginization());
        spotCheckBase1.setCheckStep(spotCheckBase.getCheckStep());
        spotCheckBase1.setCheckBasement(spotCheckBase.getCheckBasement());
        spotCheckBase1.setCheckCompanyType(spotCheckBase.getCheckCompanyType());
        spotCheckBase1.setBuyMoney(spotCheckBase.getBuyMoney());
        spotCheckBase1.setSampleName(spotCheckBase.getSampleName());
        spotCheckBase1.setSamplePerson(spotCheckBase.getSamplePerson());
        spotCheckBase1.setCheckResult(spotCheckBase.getCheckResult());
        spotCheckBase1.setResultItem(spotCheckBase.getResultItem());
        spotCheckBase1.setSpecifications(spotCheckBase.getSpecifications());
        spotCheckBase1.setBatch(spotCheckBase.getBatch());
        spotCheckBase1.setCheckMoney(spotCheckBase.getCheckMoney());
        spotCheckBase1.setSampleMoney(spotCheckBase.getSampleMoney());
        spotCheckBase1.setTeam(spotCheckBase.getTeam());
        spotCheckBase1.setSupervisor(spotCheckBase.getSupervisor());
        spotCheckBase1.setSiginal(spotCheckBase.getSiginal());
        spotCheckBase1.setDateType(spotCheckBase.getDateType());
        spotCheckBase1.setDate(spotCheckBase.getDate());
        spotCheckBase1.setSampleNo(spotCheckBase.getSampleNo());
        spotCheckBase1.setDay(spotCheckBase.getDay());
        spotCheckBase1.setStandard(spotCheckBase.getStandard());
        spotCheckBase1.setPromissionNo(spotCheckBase.getPromissionNo());
        spotCheckBase1.setProduceName(spotCheckBase.getProduceName());
        spotCheckBase1.setProduceAddress(spotCheckBase.getProduceAddress());
        spotCheckBase1.setProduceContact(spotCheckBase.getProduceContact());
        spotCheckBase1.setProducePhone(spotCheckBase.getProducePhone());
        spotCheckBase1.setSpotCheckNo(spotCheckBase.getSpotCheckNo());
        spotCheckBase1.setFoodType(spotCheckBase.getFoodType());
        spotCheckBase1.setDisposalType(spotCheckBase.getDisposalType());
        spotCheckBase1.setRemarke(spotCheckBase.getRemarke());
        spotCheckBase1.setDocument(spotCheckBase.getDocument());
        spotCheckBase1.setState(spotCheckBase.getState());
        spotCheckBase1.setOperator("操作人");
        spotCheckBase1.setOperatorIp("124.124.124");
        spotCheckBase1.setOperatorTime(new Date());


        //TODO: sendEmail

        spotCheckBaseMapper.updateByPrimaryKeySelective(spotCheckBase1);
    }

    @Override
    public void delete(int id) {
        SpotCheckBase spotCheckBase = spotCheckBaseMapper.selectByPrimaryKey(id);
        if(spotCheckBase==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        spotCheckBaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void updateRecord(int id)
    {
        SpotCheckBase before = spotCheckBaseMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新企业不存在");
        }
        spotCheckBaseMapper.updateRecord(id);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type) {

        List<SpotCheckBase> spotCheckBaseList = new ArrayList<>();
        if(type == 3){
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        SpotCheckBase spotCheckBase = new SpotCheckBase();
                        HSSFRow row = sheet.getRow(j);
                        spotCheckBase.setReportNo(ExcalUtils.handleStringHSSF(row.getCell(0)));
                        spotCheckBase.setReportDate(ExcalUtils.handleDateHSSF(row.getCell(1)));
                        spotCheckBase.setCheckDate(ExcalUtils.handleDateHSSF(row.getCell(2)));
                        spotCheckBase.setCheckType(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        spotCheckBase.setCheckCompany(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        spotCheckBase.setAddress(ExcalUtils.handleStringHSSF(row.getCell(5)));
                        spotCheckBase.setContact(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        spotCheckBase.setPhone(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        spotCheckBase.setCheckOrginization(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        spotCheckBase.setCheckStep(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        spotCheckBase.setCheckBasement(ExcalUtils.handleStringHSSF(row.getCell(10)));
                        spotCheckBase.setCheckCompanyType(ExcalUtils.handleStringHSSF(row.getCell(11)));
                        spotCheckBase.setBuyMoney(ExcalUtils.handleFloatHSSF(row.getCell(12)));
                        spotCheckBase.setSampleName(ExcalUtils.handleStringHSSF(row.getCell(13)));
                        spotCheckBase.setSamplePerson(ExcalUtils.handleStringHSSF(row.getCell(14)));
                        spotCheckBase.setCheckResult(ExcalUtils.handleStringHSSF(row.getCell(15)));
                        spotCheckBase.setResultItem(ExcalUtils.handleStringHSSF(row.getCell(16)));
                        spotCheckBase.setSpecifications(ExcalUtils.handleStringHSSF(row.getCell(17)));
                        spotCheckBase.setBatch(ExcalUtils.handleIntegerHSSF(row.getCell(18)));
                        spotCheckBase.setCheckMoney(ExcalUtils.handleFloatHSSF(row.getCell(19)));
                        spotCheckBase.setSampleMoney(ExcalUtils.handleFloatHSSF(row.getCell(20)));
                        spotCheckBase.setTeam(ExcalUtils.handleStringHSSF(row.getCell(21)));
                        spotCheckBase.setSupervisor(ExcalUtils.handleStringHSSF(row.getCell(22)));
                        spotCheckBase.setSiginal(ExcalUtils.handleStringHSSF(row.getCell(23)));
                        spotCheckBase.setDateType(ExcalUtils.handleStringHSSF(row.getCell(24)));
                        spotCheckBase.setDate(ExcalUtils.handleDateHSSF(row.getCell(25)));
                        spotCheckBase.setSampleNo(ExcalUtils.handleStringHSSF(row.getCell(26)));
                        spotCheckBase.setDay(ExcalUtils.handleStringHSSF(row.getCell(27)));
                        spotCheckBase.setStandard(ExcalUtils.handleStringHSSF(row.getCell(28)));
                        spotCheckBase.setPromissionNo(ExcalUtils.handleStringHSSF(row.getCell(29)));
                        spotCheckBase.setProduceName(ExcalUtils.handleStringHSSF(row.getCell(30)));
                        spotCheckBase.setProduceAddress(ExcalUtils.handleStringHSSF(row.getCell(31)));
                        spotCheckBase.setProduceContact(ExcalUtils.handleStringHSSF(row.getCell(32)));
                        spotCheckBase.setProducePhone(ExcalUtils.handleStringHSSF(row.getCell(33)));
                        spotCheckBase.setSpotCheckNo(ExcalUtils.handleStringHSSF(row.getCell(34)));
                        spotCheckBase.setFoodType(ExcalUtils.handleStringHSSF(row.getCell(35)));
                        spotCheckBase.setDisposalType(ExcalUtils.handleStringHSSF(row.getCell(36)));
                        spotCheckBase.setRemarke(ExcalUtils.handleStringHSSF(row.getCell(37)));
                        spotCheckBase.setDocument("");
                        spotCheckBase.setState(ExcalUtils.handleStringHSSF(row.getCell(38)));
                        spotCheckBase.setOperator("操作人");
                        spotCheckBase.setOperatorIp("123.123.123");
                        spotCheckBase.setOperatorTime(new Date());
                        if (1==1)
                        {
                            spotCheckBaseList.add(spotCheckBase);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == 7){
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        SpotCheckBase spotCheckBase = new SpotCheckBase();
                        XSSFRow row = sheet.getRow(j);
                        spotCheckBase.setReportNo(ExcalUtils.handleStringXSSF(row.getCell(0)));
                        spotCheckBase.setReportDate(ExcalUtils.handleDateXSSF(row.getCell(1)));
                        spotCheckBase.setCheckDate(ExcalUtils.handleDateXSSF(row.getCell(2)));
                        spotCheckBase.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        spotCheckBase.setCheckCompany(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        spotCheckBase.setAddress(ExcalUtils.handleStringXSSF(row.getCell(5)));
                        spotCheckBase.setContact(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        spotCheckBase.setPhone(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        spotCheckBase.setCheckOrginization(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        spotCheckBase.setCheckStep(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        spotCheckBase.setCheckBasement(ExcalUtils.handleStringXSSF(row.getCell(10)));
                        spotCheckBase.setCheckCompanyType(ExcalUtils.handleStringXSSF(row.getCell(11)));
                        spotCheckBase.setBuyMoney(ExcalUtils.handleFloatXSSF(row.getCell(12)));
                        spotCheckBase.setSampleName(ExcalUtils.handleStringXSSF(row.getCell(13)));
                        spotCheckBase.setSamplePerson(ExcalUtils.handleStringXSSF(row.getCell(14)));
                        spotCheckBase.setCheckResult(ExcalUtils.handleStringXSSF(row.getCell(15)));
                        spotCheckBase.setResultItem(ExcalUtils.handleStringXSSF(row.getCell(16)));
                        spotCheckBase.setSpecifications(ExcalUtils.handleStringXSSF(row.getCell(17)));
                        spotCheckBase.setBatch(ExcalUtils.handleIntegerXSSF(row.getCell(18)));
                        spotCheckBase.setCheckMoney(ExcalUtils.handleFloatXSSF(row.getCell(19)));
                        spotCheckBase.setSampleMoney(ExcalUtils.handleFloatXSSF(row.getCell(20)));
                        spotCheckBase.setTeam(ExcalUtils.handleStringXSSF(row.getCell(21)));
                        spotCheckBase.setSupervisor(ExcalUtils.handleStringXSSF(row.getCell(22)));
                        spotCheckBase.setSiginal(ExcalUtils.handleStringXSSF(row.getCell(23)));
                        spotCheckBase.setDateType(ExcalUtils.handleStringXSSF(row.getCell(24)));
                        spotCheckBase.setDate(ExcalUtils.handleDateXSSF(row.getCell(25)));
                        spotCheckBase.setSampleNo(ExcalUtils.handleStringXSSF(row.getCell(26)));
                        spotCheckBase.setDay(ExcalUtils.handleStringXSSF(row.getCell(27)));
                        spotCheckBase.setStandard(ExcalUtils.handleStringXSSF(row.getCell(28)));
                        spotCheckBase.setPromissionNo(ExcalUtils.handleStringXSSF(row.getCell(29)));
                        spotCheckBase.setProduceName(ExcalUtils.handleStringXSSF(row.getCell(30)));
                        spotCheckBase.setProduceAddress(ExcalUtils.handleStringXSSF(row.getCell(31)));
                        spotCheckBase.setProduceContact(ExcalUtils.handleStringXSSF(row.getCell(32)));
                        spotCheckBase.setProducePhone(ExcalUtils.handleStringXSSF(row.getCell(33)));
                        spotCheckBase.setSpotCheckNo(ExcalUtils.handleStringXSSF(row.getCell(34)));
                        spotCheckBase.setFoodType(ExcalUtils.handleStringXSSF(row.getCell(35)));
                        spotCheckBase.setDisposalType(ExcalUtils.handleStringXSSF(row.getCell(36)));
                        spotCheckBase.setRemarke(ExcalUtils.handleStringXSSF(row.getCell(37)));
                        spotCheckBase.setDocument("");
                        spotCheckBase.setState(ExcalUtils.handleStringXSSF(row.getCell(38)));
                        spotCheckBase.setOperator("操作人");
                        spotCheckBase.setOperatorIp("123.123.123");
                        spotCheckBase.setOperatorTime(new Date());
                        if (1==1)
                        {
                            spotCheckBaseList.add(spotCheckBase);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }
        spotCheckBaseMapper.batchInsert(spotCheckBaseList);
    }
}
