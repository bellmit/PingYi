package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.GoodsSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatGoodsService;
import com.example.upc.util.ExcalUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class FormatGoodsServiceImpl implements FormatGoodsService {

    @Autowired
    FormatGoodsInMapper formatGoodsInMapper;
    @Autowired
    FormatGoodsOutMapper formatGoodsOutMapper;
    @Autowired
    FormatGoodsMapper formatGoodsMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    ViewFormatGoodsMapper viewFormatGoodsMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;


    @Override
    public PageResult getPageIn(PageQuery pageQuery, Integer id, GoodsSearchParam goodsSearchParam) {

        int count=formatGoodsInMapper.countList(id,goodsSearchParam);
        if (count > 0) {
            List<FormatGoodsIn> fgiList = formatGoodsInMapper.getPage(pageQuery,id, goodsSearchParam);
            PageResult<FormatGoodsIn> pageResult = new PageResult<>();
            pageResult.setData(fgiList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatGoodsIn> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageInSup(PageQuery pageQuery, GoodsSearchParam goodsSearchParam) {

        int count=formatGoodsInMapper.countListSup(goodsSearchParam);
        if (count > 0) {
            List<FormatGoodsSupParam> fgiList = formatGoodsInMapper.getPageSup(pageQuery, goodsSearchParam);
            PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
            pageResult.setData(fgiList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageInAdmin(PageQuery pageQuery, GoodsSearchParam goodsSearchParam) {

        int count=formatGoodsInMapper.countListAdmin(goodsSearchParam);
        if (count > 0) {
            List<FormatGoodsSupParam> fgiList = formatGoodsInMapper.getPageAdmin(pageQuery,goodsSearchParam);
            PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
            pageResult.setData(fgiList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
        return pageResult;
    }


    @Override
    public PageResult getPageOut(PageQuery pageQuery, Integer id, GoodsSearchParam goodsSearchParam) {
        int count=formatGoodsOutMapper.countList(id,goodsSearchParam);
        if (count > 0) {
            List<FormatGoodsOut> fgoList = formatGoodsOutMapper.getPage(pageQuery,id, goodsSearchParam);
            PageResult<FormatGoodsOut> pageResult = new PageResult<>();
            pageResult.setData(fgoList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatGoodsOut> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageOutSup(PageQuery pageQuery, GoodsSearchParam goodsSearchParam) {

        int count=formatGoodsOutMapper.countListSup(goodsSearchParam);
        if (count > 0) {
            List<FormatGoodsSupParam> fgiList = formatGoodsOutMapper.getPageSup(pageQuery, goodsSearchParam);
            PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
            pageResult.setData(fgiList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
        return pageResult;
    }


    @Override
    public PageResult getPageOutAdmin(PageQuery pageQuery, GoodsSearchParam goodsSearchParam) {
        int count=formatGoodsOutMapper.countListAdmin(goodsSearchParam);
        if (count > 0) {
            List<FormatGoodsSupParam> fgoList = formatGoodsOutMapper.getPageAdmin(pageQuery, goodsSearchParam);
            PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
            pageResult.setData(fgoList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatGoodsSupParam> pageResult = new PageResult<>();
        return pageResult;
    }


    @Override
    @Transactional
    public void insertIn(FormatGoodsParam formatGoodsParam, SysUser sysUser) {
        ValidationResult result = validator.validate(formatGoodsParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatGoodsIn formatGoodsIn1 = new FormatGoodsIn();
        BeanUtils.copyProperties(formatGoodsParam,formatGoodsIn1);
        formatGoodsIn1.setUnit(sysUser.getInfoId());
        formatGoodsIn1.setOperator("操作人");
        formatGoodsIn1.setOperatorIp("124.124.124");
        formatGoodsIn1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatGoodsInMapper.insertSelective(formatGoodsIn1);

    }

    @Override
    @Transactional
    public void insertOut(FormatGoodsParam formatGoodsParam, SysUser sysUser) {
        ValidationResult result = validator.validate(formatGoodsParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        ViewFormatGoods viewFormatGoods = viewFormatGoodsMapper.selectByUnit(sysUser.getInfoId(),formatGoodsParam.getType(),formatGoodsParam.getName(),formatGoodsParam.getSupplier(),formatGoodsParam.getBrand());
        if (viewFormatGoods.getTotal().floatValue() - formatGoodsParam.getNum() < 0 )
        {
            throw new BusinessException(EmBusinessError.UPDATE_NOUSE);
        }
        FormatGoodsOut formatGoodsOut1 = new FormatGoodsOut();
        BeanUtils.copyProperties(formatGoodsParam,formatGoodsOut1);
        formatGoodsOut1.setUnit(sysUser.getInfoId());
        formatGoodsOut1.setOperator("操作人");
        formatGoodsOut1.setOperatorIp("124.124.124");
        formatGoodsOut1.setOperatorTime(new Date());

        // TODO: sendEmail

        formatGoodsOutMapper.insertSelective(formatGoodsOut1);

    }
    @Override
    public void deleteIn(int fgiId) {
        FormatGoodsIn formatGoodsIn = formatGoodsInMapper.selectByPrimaryKey(fgiId);
        if(formatGoodsIn==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        //删除的同时还要把库存中进货的这个数量去掉否则会保留数据
        ViewFormatGoods viewFormatGoods = viewFormatGoodsMapper.selectByUnit(formatGoodsIn.getUnit(),formatGoodsIn.getType(), formatGoodsIn.getName(),formatGoodsIn.getSupplier(),formatGoodsIn.getBrand());
        if (viewFormatGoods.getTotal().floatValue() - formatGoodsIn.getNum() < 0 )
        {
            throw new BusinessException(EmBusinessError.UPDATE_NOUSE);
        }
        else
                formatGoodsInMapper.deleteByPrimaryKey(fgiId);
    }

    @Override
    public void deleteOut(int fgoId) {
        FormatGoodsOut formatGoodsOut = formatGoodsOutMapper.selectByPrimaryKey(fgoId);
        if(formatGoodsOut==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatGoodsOutMapper.deleteByPrimaryKey(fgoId);
    }

    @Override
    @Transactional
    public void updateIn(FormatGoodsParam formatGoodsParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatGoodsParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatGoodsIn formatGoodsIn2 = formatGoodsInMapper.selectByPrimaryKey(formatGoodsParam.getId());
        if(formatGoodsIn2==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        ViewFormatGoods viewFormatGoods = viewFormatGoodsMapper.selectByUnit(sysUser.getInfoId(),formatGoodsParam.getType(), formatGoodsParam.getName(),formatGoodsParam.getSupplier(),formatGoodsParam.getBrand());
        if (viewFormatGoods.getTotal().floatValue()+formatGoodsParam.getNum()-formatGoodsIn2.getNum() < 0 )
        {
            throw new BusinessException(EmBusinessError.UPDATE_NOUSE);
        }
        FormatGoodsIn formatGoodsIn1 = new FormatGoodsIn();
        BeanUtils.copyProperties(formatGoodsParam,formatGoodsIn1);
        formatGoodsIn1.setUnit(sysUser.getInfoId());
        formatGoodsIn1.setOperator("操作人");
        formatGoodsIn1.setOperatorIp("124.124.124");
        formatGoodsIn1.setOperatorTime(new Date());
        // TODO: sendEmail

        formatGoodsInMapper.updateByPrimaryKeySelective(formatGoodsIn1);
    }
    @Override
    @Transactional
    public void updateOut(FormatGoodsParam formatGoodsParam, SysUser sysUser) {
        ValidationResult result = validator.validate(formatGoodsParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatGoodsOut formatGoodsOut2 = formatGoodsOutMapper.selectByPrimaryKey(formatGoodsParam.getId());
        if(formatGoodsOut2==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        ViewFormatGoods viewFormatGoods = viewFormatGoodsMapper.selectByUnit(sysUser.getInfoId(),formatGoodsParam.getType(), formatGoodsParam.getName(),formatGoodsParam.getSupplier(),formatGoodsParam.getBrand());
            if ((viewFormatGoods.getTotal().floatValue()+formatGoodsOut2.getNum()-formatGoodsParam.getNum())<0)
            {
                throw new BusinessException(EmBusinessError.UPDATE_NOUSE);
            }
            FormatGoodsOut formatGoodsOut1 = new FormatGoodsOut();
            BeanUtils.copyProperties(formatGoodsParam,formatGoodsOut1);
            formatGoodsOut1.setUnit(sysUser.getInfoId());
            formatGoodsOut1.setOperator("操作人");
            formatGoodsOut1.setOperatorIp("124.124.124");
            formatGoodsOut1.setOperatorTime(new Date());
            // TODO: sendEmail

        formatGoodsOutMapper.updateByPrimaryKeySelective(formatGoodsOut1);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    public void importExcelIn(MultipartFile file, Integer type, SysUser sysUser) {
//        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
//        Map<String,Integer> enterpriseMap = new HashMap<>();
//        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
//        {enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());}
        //取出企业名和企业ID

//        List<SysArea> sysAreaList = sysAreaMapper.getAllArea();
//        Map<String,Integer> areaMap = new HashMap<>();
//        for (SysArea sysArea : sysAreaList)
//        {areaMap.put(sysArea.getName(),sysArea.getId());}
//        //取出地区ID和名称

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"失败");
        }

        List<FormatGoodsIn> formatGoodsInList = new ArrayList<>();
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
                        FormatGoodsIn formatGoodsIn = new FormatGoodsIn();
                        HSSFRow row = sheet.getRow(j);
                        formatGoodsIn.setUnit(sysUser.getInfoId());
                        formatGoodsIn.setType(handleStringHSSF(row.getCell(0)));
                        formatGoodsIn.setName(handleStringHSSF(row.getCell(1)));
                        formatGoodsIn.setBrand(handleStringHSSF(row.getCell(2)));
                        formatGoodsIn.setWeight(handleStringHSSF(row.getCell(3)));
                        formatGoodsIn.setTime(handleDateHSSF(row.getCell(4)));
                        formatGoodsIn.setDay(handleStringHSSF(row.getCell(5)));
                        formatGoodsIn.setGoodsType(handleStringHSSF(row.getCell(6)));
                        formatGoodsIn.setManufacturer(handleStringHSSF(row.getCell(7)));
                        formatGoodsIn.setSupplier(handleStringHSSF(row.getCell(8)));
                        formatGoodsIn.setNum(ExcalUtils.handleFloatHSSF(row.getCell(9)));
                        formatGoodsIn.setDate(handleDateHSSF(row.getCell(10)));
                        formatGoodsIn.setPerson(handleStringHSSF(row.getCell(11)));
                        formatGoodsIn.setDocument("");
                        formatGoodsIn.setOperator("操作人");
                        formatGoodsIn.setOperatorIp("123.123.123");
                        formatGoodsIn.setOperatorTime(new Date());
                        formatGoodsInList.add(formatGoodsIn);
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
                        FormatGoodsIn formatGoodsIn = new FormatGoodsIn();
                        XSSFRow row = sheet.getRow(j);
                        formatGoodsIn.setUnit(sysUser.getInfoId());
                        formatGoodsIn.setType(handleStringXSSF(row.getCell(0)));
                        formatGoodsIn.setName(handleStringXSSF(row.getCell(1)));
                        formatGoodsIn.setBrand(handleStringXSSF(row.getCell(2)));
                        formatGoodsIn.setWeight(handleStringXSSF(row.getCell(3)));
                        formatGoodsIn.setTime(handleDateXSSF(row.getCell(4)));
                        formatGoodsIn.setDay(handleStringXSSF(row.getCell(5)));
                        formatGoodsIn.setGoodsType(handleStringXSSF(row.getCell(6)));
                        formatGoodsIn.setManufacturer(handleStringXSSF(row.getCell(7)));
                        formatGoodsIn.setSupplier(handleStringXSSF(row.getCell(8)));
                        formatGoodsIn.setNum(ExcalUtils.handleFloatXSSF(row.getCell(9)));
                        formatGoodsIn.setDate(handleDateXSSF(row.getCell(10)));
                        formatGoodsIn.setPerson(handleStringXSSF(row.getCell(11)));
                        formatGoodsIn.setDocument("");
                        formatGoodsIn.setOperator("操作人");
                        formatGoodsIn.setOperatorIp("123.123.123");
                        formatGoodsIn.setOperatorTime(new Date());
                        formatGoodsInList.add(formatGoodsIn);
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }
        formatGoodsInMapper.batchInsert(formatGoodsInList);
    }

    @Override
    @Transactional
    public void importExcelOut(MultipartFile file, Integer type, SysUser sysUser) {
//        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
//        Map<String,Integer> enterpriseMap = new HashMap<>();
//        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
//        {enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());}
        //取出企业名和企业ID

//        List<SysArea> sysAreaList = sysAreaMapper.getAllArea();
//        Map<String,Integer> areaMap = new HashMap<>();
//        for (SysArea sysArea : sysAreaList)
//        {areaMap.put(sysArea.getName(),sysArea.getId());}
//        //取出地区ID和名称

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"失败");
        }

        List<FormatGoodsOut> formatGoodsOutList = new ArrayList<>();
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
                        FormatGoodsOut formatGoodsOut = new FormatGoodsOut();
                        HSSFRow row = sheet.getRow(j);
                        formatGoodsOut.setUnit(sysUser.getInfoId());
                        formatGoodsOut.setType(handleStringHSSF(row.getCell(0)));
                        formatGoodsOut.setName(handleStringHSSF(row.getCell(1)));
                        formatGoodsOut.setBrand(handleStringHSSF(row.getCell(2)));
                        formatGoodsOut.setWeight(handleStringHSSF(row.getCell(3)));
                        formatGoodsOut.setTime(handleDateHSSF(row.getCell(4)));
                        formatGoodsOut.setDay(handleStringHSSF(row.getCell(5)));
                        formatGoodsOut.setGoodsType(handleStringHSSF(row.getCell(6)));
                        formatGoodsOut.setManufacturer(handleStringHSSF(row.getCell(7)));
                        formatGoodsOut.setSupplier(handleStringHSSF(row.getCell(8)));
                        formatGoodsOut.setNum(ExcalUtils.handleFloatHSSF(row.getCell(9)));
                        formatGoodsOut.setDate(handleDateHSSF(row.getCell(10)));
                        formatGoodsOut.setPerson(handleStringHSSF(row.getCell(11)));
                        formatGoodsOut.setDocument("");
                        formatGoodsOut.setOperator("操作人");
                        formatGoodsOut.setOperatorIp("123.123.123");
                        formatGoodsOut.setOperatorTime(new Date());
                        formatGoodsOutList.add(formatGoodsOut);
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
                        FormatGoodsOut formatGoodsOut = new FormatGoodsOut();
                        XSSFRow row = sheet.getRow(j);
                        formatGoodsOut.setUnit(sysUser.getInfoId());
                        formatGoodsOut.setType(handleStringXSSF(row.getCell(0)));
                        formatGoodsOut.setName(handleStringXSSF(row.getCell(1)));
                        formatGoodsOut.setBrand(handleStringXSSF(row.getCell(2)));
                        formatGoodsOut.setWeight(handleStringXSSF(row.getCell(3)));
                        formatGoodsOut.setTime(handleDateXSSF(row.getCell(4)));
                        formatGoodsOut.setDay(handleStringXSSF(row.getCell(5)));
                        formatGoodsOut.setGoodsType(handleStringXSSF(row.getCell(6)));
                        formatGoodsOut.setManufacturer(handleStringXSSF(row.getCell(7)));
                        formatGoodsOut.setSupplier(handleStringXSSF(row.getCell(8)));
                        formatGoodsOut.setNum(ExcalUtils.handleFloatXSSF(row.getCell(9)));
                        formatGoodsOut.setDate(handleDateXSSF(row.getCell(10)));
                        formatGoodsOut.setPerson(handleStringXSSF(row.getCell(11)));
                        formatGoodsOut.setDocument("");
                        formatGoodsOut.setOperator("操作人");
                        formatGoodsOut.setOperatorIp("123.123.123");
                        formatGoodsOut.setOperatorTime(new Date());
                        formatGoodsOutList.add(formatGoodsOut);
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }
        formatGoodsOutMapper.batchInsert(formatGoodsOutList);
    }



    @Override
    @Transactional
    public void importExcelInEx(MultipartFile file, Integer type, SysUser sysUser) {
        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
        Map<String,Integer> enterpriseMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());}
        //取出企业名和企业ID

//        List<SysArea> sysAreaList = sysAreaMapper.getAllArea();
//        Map<String,Integer> areaMap = new HashMap<>();
//        for (SysArea sysArea : sysAreaList)
//        {areaMap.put(sysArea.getName(),sysArea.getId());}
        //取出地区ID和名称

//        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
//        if (supervisionEnterprise==null)
//        {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"失败");
//        }

        List<FormatGoodsIn> formatGoodsInList = new ArrayList<>();
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
                        FormatGoodsIn formatGoodsIn = new FormatGoodsIn();
                        HSSFRow row = sheet.getRow(j);
                        formatGoodsIn.setUnit(enterpriseMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        formatGoodsIn.setType(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        formatGoodsIn.setName(ExcalUtils.handleStringHSSF(row.getCell(2)));
                        formatGoodsIn.setBrand(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        formatGoodsIn.setWeight(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        formatGoodsIn.setTime(ExcalUtils.handleDateHSSF(row.getCell(5)));
                        formatGoodsIn.setGoodsType(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        formatGoodsIn.setManufacturer(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        formatGoodsIn.setSupplier(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        formatGoodsIn.setDay(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        formatGoodsIn.setNum(ExcalUtils.handleFloatHSSF(row.getCell(10)));
                        formatGoodsIn.setDate(ExcalUtils.handleDateHSSF(row.getCell(11)));
                        formatGoodsIn.setPerson(ExcalUtils.handleStringHSSF(row.getCell(12)));
                        formatGoodsIn.setDocument("");
                        formatGoodsIn.setOperator("操作人");
                        formatGoodsIn.setOperatorIp("123.123.123");
                        formatGoodsIn.setOperatorTime(new Date());
                        if (formatGoodsIn.getUnit()!=null)
                        {
                        formatGoodsInList.add(formatGoodsIn);
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
                        FormatGoodsIn formatGoodsIn = new FormatGoodsIn();
                        XSSFRow row = sheet.getRow(j);
                        formatGoodsIn.setUnit(enterpriseMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        formatGoodsIn.setType(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        formatGoodsIn.setName(ExcalUtils.handleStringXSSF(row.getCell(2)));
                        formatGoodsIn.setBrand(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        formatGoodsIn.setWeight(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        formatGoodsIn.setTime(ExcalUtils.handleDateXSSF(row.getCell(5)));
                        formatGoodsIn.setGoodsType(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        formatGoodsIn.setManufacturer(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        formatGoodsIn.setSupplier(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        formatGoodsIn.setDay(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatGoodsIn.setNum(ExcalUtils.handleFloatXSSF(row.getCell(10)));
                        formatGoodsIn.setDate(ExcalUtils.handleDateXSSF(row.getCell(11)));
                        formatGoodsIn.setPerson(ExcalUtils.handleStringXSSF(row.getCell(12)));
                        formatGoodsIn.setDocument("");
                        formatGoodsIn.setOperator("操作人");
                        formatGoodsIn.setOperatorIp("123.123.123");
                        formatGoodsIn.setOperatorTime(new Date());
                        if (formatGoodsIn.getUnit()!=null&&row.getCell(5)!=null&&row.getCell(11)!=null)
                        {
                            formatGoodsInList.add(formatGoodsIn);
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
        formatGoodsInMapper.batchInsertEx(formatGoodsInList);
    }


    @Override
    @Transactional
    public void importExcelOutEx(MultipartFile file, Integer type, SysUser sysUser) {
        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
        Map<String,Integer> enterpriseMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());}

        List<FormatGoodsOut> formatGoodsOutList = new ArrayList<>();
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
                        FormatGoodsOut formatGoodsOut = new FormatGoodsOut();
                        HSSFRow row = sheet.getRow(j);
                        formatGoodsOut.setUnit(enterpriseMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        formatGoodsOut.setType(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        formatGoodsOut.setName(ExcalUtils.handleStringHSSF(row.getCell(2)));
                        formatGoodsOut.setBrand(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        formatGoodsOut.setWeight(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        formatGoodsOut.setTime(ExcalUtils.handleDateHSSF(row.getCell(5)));
                        formatGoodsOut.setGoodsType(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        formatGoodsOut.setManufacturer(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        formatGoodsOut.setSupplier(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        formatGoodsOut.setDay(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        formatGoodsOut.setNum(ExcalUtils.handleFloatHSSF(row.getCell(10)));
                        formatGoodsOut.setDate(ExcalUtils.handleDateHSSF(row.getCell(11)));
                        formatGoodsOut.setPerson(ExcalUtils.handleStringHSSF(row.getCell(12)));
                        formatGoodsOut.setDocument("");
                        formatGoodsOut.setOperator("操作人");
                        formatGoodsOut.setOperatorIp("123.123.123");
                        formatGoodsOut.setOperatorTime(new Date());
                        if (formatGoodsOut.getUnit()!=null)
                        {
                            formatGoodsOutList.add(formatGoodsOut);
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
                        FormatGoodsOut formatGoodsOut = new FormatGoodsOut();
                        XSSFRow row = sheet.getRow(j);
                        formatGoodsOut.setUnit(enterpriseMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        formatGoodsOut.setType(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        formatGoodsOut.setName(ExcalUtils.handleStringXSSF(row.getCell(2)));
                        formatGoodsOut.setBrand(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        formatGoodsOut.setWeight(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        formatGoodsOut.setTime(ExcalUtils.handleDateXSSF(row.getCell(5)));
                        formatGoodsOut.setGoodsType(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        formatGoodsOut.setManufacturer(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        formatGoodsOut.setSupplier(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        formatGoodsOut.setDay(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatGoodsOut.setNum(ExcalUtils.handleFloatXSSF(row.getCell(10)));
                        formatGoodsOut.setDate(ExcalUtils.handleDateXSSF(row.getCell(11)));
                        formatGoodsOut.setPerson(ExcalUtils.handleStringXSSF(row.getCell(12)));
                        formatGoodsOut.setDocument("");
                        formatGoodsOut.setOperator("操作人");
                        formatGoodsOut.setOperatorIp("123.123.123");
                        formatGoodsOut.setOperatorTime(new Date());
                        if (formatGoodsOut.getUnit()!=null&&row.getCell(5)!=null&&row.getCell(11)!=null)
                        {
                            formatGoodsOutList.add(formatGoodsOut);
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
        formatGoodsOutMapper.batchInsertEx(formatGoodsOutList);
    }


    private String handleStringXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return "";
        }else {
            return xssfCell.getStringCellValue();
        }
    }
    private String handleStringHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return "";
        }else {
            return hssfCell.getStringCellValue();
        }
    }
    private  Date handleDateHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return null;
        }else {
            return hssfCell.getDateCellValue();
        }
    }
    private Date handleDateXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return null;
        }else {
            return xssfCell.getDateCellValue();
        }
    }

    private int handleIntegerXSSF(XSSFCell xssfCell){
        if(xssfCell==null){
            return 0;
        }else {
            return (int)xssfCell.getNumericCellValue();
        }
    }
    private int handleIntegerHSSF(HSSFCell hssfCell){
        if(hssfCell==null){
            return 0;
        }else {
            return (int)hssfCell.getNumericCellValue();
        }
    }

}
