package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.QuickCheckBaseSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.QuickCheckBaseService;
import com.example.upc.util.ExcalUtils;
import com.example.upc.util.MD5Util;
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
public class QuickCheckBaseServiceImpl implements QuickCheckBaseService {
    @Autowired
    QuickCheckBaseMapper quickCheckBaseMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SupervisionGaMapper supervisionGaMapper;
    @Autowired
    QuickCheckEnterpriseMapper  quickCheckEnterpriseMapper;
    @Autowired
    SysIndustryMapper sysIndustryMapper;

    //政府看
    @Override
    public PageResult getPage(PageQuery pageQuery,int id,  QuickCheckBaseSearchParam quickCheckBaseSearchParam) {
        SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(id);
        if (supervisionGa == null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERERROR);
        }
        int count=quickCheckBaseMapper.countListSup(supervisionGa.getUnitName(), quickCheckBaseSearchParam);
        if (count > 0) {
            List<QuickCheckBase> fpList = quickCheckBaseMapper.getPageSup(pageQuery, supervisionGa.getUnitName(), quickCheckBaseSearchParam);
            PageResult<QuickCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<QuickCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }
    //我看我自己
    @Override
    public PageResult getUser(PageQuery pageQuery, int id, QuickCheckBaseSearchParam quickCheckBaseSearchParam) {
        QuickCheckEnterprise quickCheckEnterprise = quickCheckEnterpriseMapper.getUser(id);
        if (quickCheckEnterprise == null)
        {
            throw new BusinessException(EmBusinessError.CHECK_USERNO);
        }
        int count=quickCheckBaseMapper.countListEnterprise(quickCheckEnterprise.getEnterpriseName(), quickCheckBaseSearchParam);
        if (count > 0) {
            List<QuickCheckBase> fpList = quickCheckBaseMapper.getPageEnterprise(pageQuery, quickCheckEnterprise.getEnterpriseName(), quickCheckBaseSearchParam);
            PageResult<QuickCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<QuickCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, QuickCheckBaseSearchParam quickCheckBaseSearchParam) {
        int count=quickCheckBaseMapper.countListAdmin(quickCheckBaseSearchParam);
        if (count > 0) {
            List<QuickCheckBase> fpList = quickCheckBaseMapper.getPageAdmin(pageQuery, quickCheckBaseSearchParam);
            PageResult<QuickCheckBase> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<QuickCheckBase> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<SysIndustry> getPageStatus(PageQuery pageQuery) {
        int count= sysIndustryMapper.countListStatus();
        if (count > 0) {
            List<SysIndustry> inspectIndustrieList = sysIndustryMapper.getPageStatus(pageQuery);
            PageResult<SysIndustry> pageResult = new PageResult<>();
            pageResult.setData(inspectIndustrieList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysIndustry> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(QuickCheckBase quickCheckBase, SysUser sysUser) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        QuickCheckBase quickCheckBase1 = new QuickCheckBase();
        quickCheckBase1.setDate(quickCheckBase.getDate());
        quickCheckBase1.setCheckName(quickCheckBase.getCheckName());

        if (sysUser.getUserType()==6)
        {
            quickCheckBase1.setCheckCompany("预留学校表单位名称");
            quickCheckBase1.setAddress("预留学校表地址");
        }
        else
            {
                quickCheckBase1.setCheckCompany(quickCheckBase.getCheckCompany());
                quickCheckBase1.setAddress(quickCheckBase.getAddress());
            }

        quickCheckBase1.setMainOperationType(quickCheckBase.getMainOperationType());
        quickCheckBase1.setCharger(quickCheckBase.getCharger());
        quickCheckBase1.setPhone(quickCheckBase.getPhone());
        quickCheckBase1.setCheckItems(quickCheckBase.getCheckItems());
        quickCheckBase1.setCheckResult(quickCheckBase.getCheckResult());
        quickCheckBase1.setBuyMoney(quickCheckBase.getBuyMoney());
        quickCheckBase1.setCheckBatch(quickCheckBase.getCheckBatch());

        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = supervisionGaMapper.selectByPrimaryKey(sysUser.getInfoId());
            quickCheckBase1.setTeam(supervisionGa.getUnitName());
            quickCheckBase1.setState("已上报");
        }
        else
        {
            quickCheckBase1.setTeam(quickCheckBase.getTeam());
            quickCheckBase1.setState("未上报");
        }

        if (sysUser.getUserType()==4)
        {
            QuickCheckEnterprise quickCheckEnterprise = quickCheckEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
            quickCheckBase1.setMarket(quickCheckEnterprise.getEnterpriseName());
        }
        else
        {
            quickCheckBase1.setMarket(quickCheckBase.getMarket());
        }

        quickCheckBase1.setCheckPerson(quickCheckBase.getCheckPerson());
        quickCheckBase1.setCheckProduct(quickCheckBase.getCheckProduct());
        quickCheckBase1.setRemark(quickCheckBase.getRemark());
        quickCheckBase1.setDocument(quickCheckBase.getDocument());
        quickCheckBase1.setOperator("操作人");
        quickCheckBase1.setOperatorIp("124.124.124");
        quickCheckBase1.setOperatorTime(new Date());

         //TODO: sendEmail

            quickCheckBaseMapper.insertSelective(quickCheckBase1);
    }

    @Override
    @Transactional
    public void update(QuickCheckBase quickCheckBase) {

//        ValidationResult result = validator.validate(formatWasteParam);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        QuickCheckBase before = quickCheckBaseMapper.selectByPrimaryKey(quickCheckBase.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新企业不存在");
        }

        QuickCheckBase quickCheckBase1 = new QuickCheckBase();
        quickCheckBase1.setId(quickCheckBase.getId());
        quickCheckBase1.setDate(quickCheckBase.getDate());
        quickCheckBase1.setCheckName(quickCheckBase.getCheckName());
        quickCheckBase1.setCheckCompany(quickCheckBase.getCheckCompany());
        quickCheckBase1.setAddress(quickCheckBase.getAddress());
        quickCheckBase1.setMainOperationType(quickCheckBase.getMainOperationType());
        quickCheckBase1.setCharger(quickCheckBase.getCharger());
        quickCheckBase1.setPhone(quickCheckBase.getPhone());
        quickCheckBase1.setCheckItems(quickCheckBase.getCheckItems());
        quickCheckBase1.setCheckResult(quickCheckBase.getCheckResult());
        quickCheckBase1.setBuyMoney(quickCheckBase.getBuyMoney());
        quickCheckBase1.setCheckBatch(quickCheckBase.getCheckBatch());
        quickCheckBase1.setTeam(quickCheckBase.getTeam());
        quickCheckBase1.setMarket(quickCheckBase.getMarket());
        quickCheckBase1.setCheckPerson(quickCheckBase.getCheckPerson());
        quickCheckBase1.setCheckProduct(quickCheckBase.getCheckProduct());
        quickCheckBase1.setRemark(quickCheckBase.getRemark());
        quickCheckBase1.setDocument(quickCheckBase.getDocument());
        quickCheckBase1.setState(quickCheckBase.getState());
        quickCheckBase1.setOperator("操作人");
        quickCheckBase1.setOperatorIp("124.124.124");
        quickCheckBase1.setOperatorTime(new Date());

        //TODO: sendEmail

        quickCheckBaseMapper.updateByPrimaryKeySelective(quickCheckBase1);
    }

    @Override
    public void delete(int id) {
        QuickCheckBase quickCheckBase = quickCheckBaseMapper.selectByPrimaryKey(id);
        if(quickCheckBase==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        quickCheckBaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void updateRecord(int id)
    {
        QuickCheckBase before = quickCheckBaseMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新企业不存在");
        }
        quickCheckBaseMapper.updateRecord(id);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type) {

//        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
//        if (supervisionEnterprise==null)
//        {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业不存在");
//        }

        List<QuickCheckBase> quickCheckBaseList = new ArrayList<>();
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
                        QuickCheckBase quickCheckBase = new QuickCheckBase();
                        HSSFRow row = sheet.getRow(j);
                        quickCheckBase.setDate(ExcalUtils.handleDateHSSF(row.getCell(0)));
                        quickCheckBase.setCheckName(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        quickCheckBase.setCheckCompany(ExcalUtils.handleStringHSSF(row.getCell(2)));
                        quickCheckBase.setAddress(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        quickCheckBase.setMainOperationType(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        quickCheckBase.setCharger(ExcalUtils.handleStringHSSF(row.getCell(5)));
                        quickCheckBase.setPhone(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        quickCheckBase.setCheckItems(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        quickCheckBase.setCheckResult(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        quickCheckBase.setBuyMoney(ExcalUtils.handleFloatHSSF(row.getCell(9)));
                        quickCheckBase.setCheckBatch(ExcalUtils.handleIntegerHSSF(row.getCell(10)));
                        quickCheckBase.setTeam(ExcalUtils.handleStringHSSF(row.getCell(11)));
                        quickCheckBase.setMarket(ExcalUtils.handleStringHSSF(row.getCell(12)));
                        quickCheckBase.setCheckPerson(ExcalUtils.handleStringHSSF(row.getCell(13)));
                        quickCheckBase.setCheckProduct(ExcalUtils.handleStringHSSF(row.getCell(14)));
                        quickCheckBase.setRemark(ExcalUtils.handleStringHSSF(row.getCell(15)));
                        quickCheckBase.setDocument("");
                        quickCheckBase.setState(ExcalUtils.handleStringHSSF(row.getCell(16)));
                        quickCheckBase.setOperator("操作人");
                        quickCheckBase.setOperatorIp("123.123.123");
                        quickCheckBase.setOperatorTime(new Date());
                        if (1==1)
                        {
                            quickCheckBaseList.add(quickCheckBase);
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
                        QuickCheckBase quickCheckBase = new QuickCheckBase();
                        XSSFRow row = sheet.getRow(j);
                        quickCheckBase.setDate(ExcalUtils.handleDateXSSF(row.getCell(0)));
                        quickCheckBase.setCheckName(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        quickCheckBase.setCheckCompany(ExcalUtils.handleStringXSSF(row.getCell(2)));
                        quickCheckBase.setAddress(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        quickCheckBase.setMainOperationType(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        quickCheckBase.setCharger(ExcalUtils.handleStringXSSF(row.getCell(5)));
                        quickCheckBase.setPhone(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        quickCheckBase.setCheckItems(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        quickCheckBase.setCheckResult(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        quickCheckBase.setBuyMoney(ExcalUtils.handleFloatXSSF(row.getCell(9)));
                        quickCheckBase.setCheckBatch(ExcalUtils.handleIntegerXSSF(row.getCell(10)));
                        quickCheckBase.setTeam(ExcalUtils.handleStringXSSF(row.getCell(11)));
                        quickCheckBase.setMarket(ExcalUtils.handleStringXSSF(row.getCell(12)));
                        quickCheckBase.setCheckPerson(ExcalUtils.handleStringXSSF(row.getCell(13)));
                        quickCheckBase.setCheckProduct(ExcalUtils.handleStringXSSF(row.getCell(14)));
                        quickCheckBase.setRemark(ExcalUtils.handleStringXSSF(row.getCell(15)));
                        quickCheckBase.setDocument("");
                        quickCheckBase.setState(ExcalUtils.handleStringXSSF(row.getCell(16)));
                        quickCheckBase.setOperator("操作人");
                        quickCheckBase.setOperatorIp("123.123.123");
                        quickCheckBase.setOperatorTime(new Date());
                        if (1==1)
                        {
                            quickCheckBaseList.add(quickCheckBase);
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
        quickCheckBaseMapper.batchInsert(quickCheckBaseList);
    }

}
