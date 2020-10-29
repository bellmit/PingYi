package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.OriginRecordSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatOriginRecordService;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormatOriginRecordServiceImpl implements FormatOriginRecordService {
    @Autowired
    FormatOriginRecordMapper formatOriginRecordMapper;
    @Autowired
    FormatOriginRecordConfigMapper formatOriginRecordConfigMapper;

    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    SysDeptMapper sysDeptMapper;
    @Autowired
    SysDeptAreaMapper sysDeptAreaMapper;
    @Autowired
    ViewFormatOriginRecordMapper viewFormatOriginRecordMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    private ValidatorImpl validator;


    @Override
    public PageResult<ViewFormatOriginRecordSupParam> getPage(PageQuery pageQuery, OriginRecordSearchParam originRecordSearchParam) {
        int count=viewFormatOriginRecordMapper.countListSup(originRecordSearchParam);
        if (count > 0) {
            List<ViewFormatOriginRecordSupParam> forList = viewFormatOriginRecordMapper.getPage(pageQuery, originRecordSearchParam);
            PageResult<ViewFormatOriginRecordSupParam> pageResult = new PageResult<>();
            pageResult.setData(forList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewFormatOriginRecordSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<ViewFormatOriginRecordSupParam> getPageAdmin(PageQuery pageQuery, OriginRecordSearchParam originRecordSearchParam) {
        int count=viewFormatOriginRecordMapper.countListAdmin(originRecordSearchParam);
        if (count > 0) {
            List<ViewFormatOriginRecordSupParam> forList = viewFormatOriginRecordMapper.getPageAdmin(pageQuery, originRecordSearchParam);
            PageResult<ViewFormatOriginRecordSupParam> pageResult = new PageResult<>();
            pageResult.setData(forList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewFormatOriginRecordSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<ViewFormatOriginRecord> getPageEnterprise(PageQuery pageQuery, Integer id,OriginRecordSearchParam originRecordSearchParam) {

        int count=formatOriginRecordMapper.countListEnterprise(id, originRecordSearchParam);
        if (count > 0) {
            List<ViewFormatOriginRecord> fdList = viewFormatOriginRecordMapper.getPageEnterprise(pageQuery,id,originRecordSearchParam);
            PageResult<ViewFormatOriginRecord> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewFormatOriginRecord> pageResult = new PageResult<>();
        return pageResult;
    }


    @Override
    public void delete(int fpId) {
        FormatOriginRecord formatOriginRecord = formatOriginRecordMapper.selectByPrimaryKey(fpId);
        if(formatOriginRecord==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatOriginRecordMapper.deleteByPrimaryKey(fpId);
        formatOriginRecordConfigMapper.deleteByParentId(fpId);
    }

    @Override
    public FormatOriginRecordParam getById(int id) {
        FormatOriginRecord formatOriginRecord = formatOriginRecordMapper.selectByPrimaryKey(id);
        FormatOriginRecordEnParam formatOriginRecordEnParam = new FormatOriginRecordEnParam();
        BeanUtils.copyProperties(formatOriginRecord, formatOriginRecordEnParam);
        FormatOriginRecordParam formatOriginRecordParam = new FormatOriginRecordParam();
        BeanUtils.copyProperties(formatOriginRecordEnParam, formatOriginRecordParam);
        List<FormatOriginRecordConfig> list = formatOriginRecordConfigMapper.selectByParentId(id);
        formatOriginRecordParam.setList(list);
        return formatOriginRecordParam;
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {

        FormatOriginRecordParam formatOriginRecordParam = JSONObject.parseObject(json,FormatOriginRecordParam.class);
        FormatOriginRecord formatOriginRecord = new FormatOriginRecord();
        BeanUtils.copyProperties(formatOriginRecordParam,formatOriginRecord);
        FormatOriginRecordConfig formatOriginRecordConfig = new FormatOriginRecordConfig();
        BeanUtils.copyProperties(formatOriginRecordParam,formatOriginRecordConfig);


        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业不存在");
        }
        formatOriginRecord.setEnterprise(sysUser.getInfoId());
        formatOriginRecord.setArea(supervisionEnterprise.getArea());
        formatOriginRecord.setOperatorIp("124.124.124");
        formatOriginRecord.setOperatorTime(new Date());
        formatOriginRecord.setOperator("zcc");
        formatOriginRecordMapper.insertSelective(formatOriginRecord);

        if(formatOriginRecord.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        formatOriginRecordConfigMapper.deleteByParentId(formatOriginRecord.getId());
        List<FormatOriginRecordConfig> formatOriginRecordConfigList = formatOriginRecordParam.getList();
        if(formatOriginRecordConfigList.size()>0){
            formatOriginRecordConfigMapper.batchInsert(formatOriginRecordConfigList.stream().map((recordConfig)->{
                ValidationResult result = validator.validate(recordConfig);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }
                recordConfig.setOperatorIp("124.124.124");
                recordConfig.setOperatorTime(new Date());
                recordConfig.setOperator("zcc");
                recordConfig.setGoodsOut(0);
                recordConfig.setGoods(0);
                recordConfig.setParentId(formatOriginRecord.getId());
                return recordConfig;}).collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void update(String json, SysUser sysUser) {
        FormatOriginRecordParam formatOriginRecordParam = JSONObject.parseObject(json,FormatOriginRecordParam.class);
        FormatOriginRecord formatOriginRecord = new FormatOriginRecord();
        BeanUtils.copyProperties(formatOriginRecordParam,formatOriginRecord);
        FormatOriginRecordConfig formatOriginRecordConfig = new FormatOriginRecordConfig();
        BeanUtils.copyProperties(formatOriginRecordParam,formatOriginRecordConfig);
        FormatOriginRecord before = formatOriginRecordMapper.selectByPrimaryKey(formatOriginRecord.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新条目不存在");
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业不存在");
        }
        formatOriginRecord.setEnterprise(sysUser.getInfoId());
        formatOriginRecord.setArea(supervisionEnterprise.getArea());
        formatOriginRecord.setOperatorIp("124.124.124");
        formatOriginRecord.setOperatorTime(new Date());
        formatOriginRecord.setOperator("zcc");
        formatOriginRecordMapper.updateByPrimaryKeySelective(formatOriginRecord);
        formatOriginRecordConfigMapper.deleteByParentId(formatOriginRecord.getId());
        List<FormatOriginRecordConfig> formatOriginRecordConfigList = formatOriginRecordParam.getList();
        if(formatOriginRecordConfigList.size()>0){
            formatOriginRecordConfigMapper.batchInsert(formatOriginRecordConfigList.stream().map((recordConfig)->{

                ValidationResult result = validator.validate(recordConfig);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }

                recordConfig.setOperatorIp("124.124.124");
                recordConfig.setOperatorTime(new Date());
                recordConfig.setOperator("zcc");
                recordConfig.setGoodsOut(0);
                recordConfig.setGoods(0);
                recordConfig.setParentId(formatOriginRecord.getId());
                return recordConfig;}).collect(Collectors.toList()));
        }
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }



    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type, SysUser sysUser) {

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业不存在");
        }
        FormatOriginRecord formatOriginRecord = new FormatOriginRecord();
        formatOriginRecord.setEnterprise(sysUser.getInfoId());
        formatOriginRecord.setArea(supervisionEnterprise.getArea());
        formatOriginRecord.setRecordTime(new Date());
        formatOriginRecord.setDocument("");
        formatOriginRecord.setOperatorIp("124.124.124");
        formatOriginRecord.setOperatorTime(new Date());
        formatOriginRecord.setOperator("zcc");
        formatOriginRecordMapper.insertSelective(formatOriginRecord);

        List<FormatOriginRecordConfig> formatOriginRecordConfigList = new ArrayList<>();
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
                        FormatOriginRecordConfig formatOriginRecordConfig = new FormatOriginRecordConfig();
                        HSSFRow row = sheet.getRow(j);
                        formatOriginRecordConfig.setParentId(formatOriginRecord.getId());
                        formatOriginRecordConfig.setSeq(handleIntegerHSSF(row.getCell(0)));
                        formatOriginRecordConfig.setOriginType1(handleStringHSSF(row.getCell(1)));
                        formatOriginRecordConfig.setOriginName(handleStringHSSF(row.getCell(2)));
                        formatOriginRecordConfig.setOriginType2(handleStringHSSF(row.getCell(3)));
                        formatOriginRecordConfig.setProduceName(handleStringHSSF(row.getCell(4)));
                        formatOriginRecordConfig.setProduceBrand(handleStringHSSF(row.getCell(5)));
                        formatOriginRecordConfig.setProduceSpecifications(handleStringHSSF(row.getCell(6)));
                        formatOriginRecordConfig.setProduceDate(handleDateHSSF(row.getCell(7)));
                        formatOriginRecordConfig.setProduceSaveTime(handleIntegerHSSF(row.getCell(8)));
                        formatOriginRecordConfig.setGoodsInDate(handleDateHSSF(row.getCell(9)));
                        formatOriginRecordConfig.setGoodsIn(handleIntegerHSSF(row.getCell(10)));
                        formatOriginRecordConfig.setGoodsInType(handleStringHSSF(row.getCell(11)));
                        formatOriginRecordConfig.setGoodsOut(handleIntegerHSSF(row.getCell(12)));
                        formatOriginRecordConfig.setGoods(handleIntegerHSSF(row.getCell(13)));
                        formatOriginRecordConfig.setDeadDate(handleDateHSSF(row.getCell(14)));
                        formatOriginRecordConfig.setSupplier(handleStringHSSF(row.getCell(15)));
                        formatOriginRecordConfig.setRecordGive(handleStringHSSF(row.getCell(16)));
                        formatOriginRecordConfig.setPerson(handleStringHSSF(row.getCell(17)));
                        formatOriginRecordConfig.setOperator("操作人");
                        formatOriginRecordConfig.setOperatorIp("123.123.123");
                        formatOriginRecordConfig.setOperatorTime(new Date());
                        formatOriginRecordConfigList.add(formatOriginRecordConfig);
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
                        FormatOriginRecordConfig formatOriginRecordConfig = new FormatOriginRecordConfig();
                        XSSFRow row = sheet.getRow(j);
                        formatOriginRecordConfig.setParentId(formatOriginRecord.getId());
                        formatOriginRecordConfig.setSeq(handleIntegerXSSF(row.getCell(0)));
                        formatOriginRecordConfig.setOriginType1(handleStringXSSF(row.getCell(1)));
                        formatOriginRecordConfig.setOriginName(handleStringXSSF(row.getCell(2)));
                        formatOriginRecordConfig.setOriginType2(handleStringXSSF(row.getCell(3)));
                        formatOriginRecordConfig.setProduceName(handleStringXSSF(row.getCell(4)));
                        formatOriginRecordConfig.setProduceBrand(handleStringXSSF(row.getCell(5)));
                        formatOriginRecordConfig.setProduceSpecifications(handleStringXSSF(row.getCell(6)));
                        formatOriginRecordConfig.setProduceDate(handleDateXSSF(row.getCell(7)));
                        formatOriginRecordConfig.setProduceSaveTime(handleIntegerXSSF(row.getCell(8)));
                        formatOriginRecordConfig.setGoodsInDate(handleDateXSSF(row.getCell(9)));
                        formatOriginRecordConfig.setGoodsIn(handleIntegerXSSF(row.getCell(10)));
                        formatOriginRecordConfig.setGoodsInType(handleStringXSSF(row.getCell(11)));
                        formatOriginRecordConfig.setGoodsOut(handleIntegerXSSF(row.getCell(12)));
                        formatOriginRecordConfig.setGoods(handleIntegerXSSF(row.getCell(13)));
                        formatOriginRecordConfig.setDeadDate(handleDateXSSF(row.getCell(14)));
                        formatOriginRecordConfig.setSupplier(handleStringXSSF(row.getCell(15)));
                        formatOriginRecordConfig.setRecordGive(handleStringXSSF(row.getCell(16)));
                        formatOriginRecordConfig.setPerson(handleStringXSSF(row.getCell(17)));
                        formatOriginRecordConfig.setOperator("操作人");
                        formatOriginRecordConfig.setOperatorIp("123.123.123");
                        formatOriginRecordConfig.setOperatorTime(new Date());
                        formatOriginRecordConfigList.add(formatOriginRecordConfig);
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }
        formatOriginRecordConfigMapper.batchInsert(formatOriginRecordConfigList);
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