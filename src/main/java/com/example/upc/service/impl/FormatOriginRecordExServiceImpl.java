package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.OriginRecordExSearchParam;
import com.example.upc.dao.FormatOriginRecordExMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.FormatOriginRecordEx;
import com.example.upc.dataobject.FormatWaste;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatOriginRecordExService;
import com.example.upc.util.ExcalUtils;
import com.example.upc.util.MapToStrUtil;
import com.example.upc.util.operateExcel.WasteExcel;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FormatOriginRecordExServiceImpl implements FormatOriginRecordExService {
    @Autowired
    FormatOriginRecordExMapper formatOriginRecordExMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult<FormatOriginRecordExEnParam> getPage(PageQuery pageQuery, OriginRecordExSearchParam originRecordExSearchParam) {
        int count=formatOriginRecordExMapper.countListSup(originRecordExSearchParam);
        if (count > 0) {
            List<FormatOriginRecordExEnParam> forList = formatOriginRecordExMapper.getPage(pageQuery, originRecordExSearchParam);
            PageResult<FormatOriginRecordExEnParam> pageResult = new PageResult<>();
            pageResult.setData(forList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatOriginRecordExEnParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<FormatOriginRecordExEnParam> getPageAdmin(PageQuery pageQuery, OriginRecordExSearchParam originRecordExSearchParam) {
        int count=formatOriginRecordExMapper.countListAdmin(originRecordExSearchParam);
        if (count > 0) {
            List<FormatOriginRecordExEnParam> forList = formatOriginRecordExMapper.getPageAdmin(pageQuery, originRecordExSearchParam);
            PageResult<FormatOriginRecordExEnParam> pageResult = new PageResult<>();
            pageResult.setData(forList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatOriginRecordExEnParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<FormatOriginRecordExEnParam> getPageEnterprise(PageQuery pageQuery, Integer id, OriginRecordExSearchParam originRecordExSearchParam) {

        int count=formatOriginRecordExMapper.countListEnterprise(id, originRecordExSearchParam);
        if (count > 0) {
            List<FormatOriginRecordExEnParam> fdList = formatOriginRecordExMapper.getPageEnterprise(pageQuery,id,originRecordExSearchParam);
            PageResult<FormatOriginRecordExEnParam> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatOriginRecordExEnParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void delete(int id) {
        FormatOriginRecordEx formatOriginRecordEx = formatOriginRecordExMapper.selectByPrimaryKey(id);
        if(formatOriginRecordEx==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatOriginRecordExMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void insert(FormatOriginRecordExParam formatOriginRecordExParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatOriginRecordExParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();
        BeanUtils.copyProperties(formatOriginRecordExParam,formatOriginRecordEx);

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }
        formatOriginRecordEx.setEnterprise(sysUser.getInfoId());
        formatOriginRecordEx.setArea(supervisionEnterprise.getArea());
        formatOriginRecordEx.setGoods(formatOriginRecordExParam.getGoodsIn());
        formatOriginRecordEx.setGoodsOut(formatOriginRecordExParam.getGoodsIn());
        formatOriginRecordEx.setOperator("?????????");
        formatOriginRecordEx.setOperatorIp("124.124.124");
        formatOriginRecordEx.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginRecordExMapper.insertSelective(formatOriginRecordEx);
    }

    @Override
    @Transactional
    public void miniInsert(List<FormatOriginRecordEx> formatOriginExtraList, SysUser sysUser){

        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }
        formatOriginExtraList.forEach(item -> {
            item.setEnterprise(sysUser.getInfoId());
            item.setArea(supervisionEnterprise.getArea());
            item.setOperator("?????????");
            item.setOperatorIp("124.124.124");
            item.setOperatorTime(new Date());
        });

        // TODO: sendEmail
        formatOriginRecordExMapper.batchInsertEx(formatOriginExtraList);
    }

    @Override
    @Transactional
    public List<FormatOriginRecordExEnParam> getRecordExByDate(FormatOriginRecordEx formatOriginRecordEx, SysUser sysUser){
        if (formatOriginRecordEx.getRecordTime()!=null)
        {
            Date endDate = new Date(formatOriginRecordEx.getRecordTime().getTime()+(long) 24 * 60 * 60 * 1000);
            return formatOriginRecordExMapper.getRecordExByDate(sysUser.getInfoId(),formatOriginRecordEx.getRecordTime(),endDate);
        }
        return formatOriginRecordExMapper.getRecordExByDate(sysUser.getInfoId(),null,null);
    }

    @Override
    @Transactional
    public List<FormatOriginRecordExListParam> getRecordExPublicByDate(FormatOriginRecordEx formatOriginRecordEx, SysUser sysUser){
        if (formatOriginRecordEx.getRecordTime()!=null)
        {
            Date endDate = new Date(formatOriginRecordEx.getRecordTime().getTime()+(long) 24 * 60 * 60 * 1000);
            return formatOriginRecordExMapper.getRecordExPublicByDate(sysUser.getInfoId(),formatOriginRecordEx.getRecordTime(),endDate);
        }
        return formatOriginRecordExMapper.getRecordExPublicByDate(sysUser.getInfoId(),null,null);
    }

    @Override
    public Object standingOriginRecord(OriginRecordExSearchParam originRecordExSearchParam, SysUser sysUser) throws IOException {
        List<FormatOriginRecordEx> formatOriginRecordExList = formatOriginRecordExMapper.getPageEnterprise2(sysUser.getInfoId(),originRecordExSearchParam);
        List<String[]> data = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        for (FormatOriginRecordEx item:formatOriginRecordExList
        ) {
            String name = item.getProducer()+"/"+item.getBrand();
            data.add(new String[]{
                    item.getOriginTypeEx(),dateFormat.format(item.getRecordTime()),item.getOriginName(),item.getOriginType(),name,item.getNetContent(),dateFormat2.format(item.getProduceTime()),
                    item.getKeepTime(),Float.toString(item.getGoodsIn()),item.getGoodsType(),item.getSupplier(),item.getState(),item.getPerson()
            });
        }
        String fileName = "????????????";
        String path = WasteExcel.getXLsx(data,"/template/??????????????????????????????.xlsx",fileName,sysUser.getInfoId());
        return path;
    }

    @Override
    @Transactional
    public void update(FormatOriginRecordExParam formatOriginRecordExParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatOriginRecordExParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if(formatOriginRecordExMapper.selectByPrimaryKey(formatOriginRecordExParam.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }

        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();
        BeanUtils.copyProperties(formatOriginRecordExParam,formatOriginRecordEx);

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }

        formatOriginRecordEx.setEnterprise(sysUser.getInfoId());
        formatOriginRecordEx.setArea(supervisionEnterprise.getArea());
        formatOriginRecordEx.setGoods(formatOriginRecordExParam.getGoodsIn());
        formatOriginRecordEx.setGoodsOut(formatOriginRecordExParam.getGoodsIn());
        formatOriginRecordEx.setOperator("?????????");
        formatOriginRecordEx.setOperatorIp("124.124.124");
        formatOriginRecordEx.setOperatorTime(new Date());

        // TODO: sendEmail

        formatOriginRecordExMapper.updateByPrimaryKeySelective(formatOriginRecordEx);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    //??????????????????
    public void importExcelEx(MultipartFile file, Integer type) {

        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
        Map<String,Integer> enterpriseMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());}

        Map<String,Integer> areaMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {areaMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getArea());}

        List<FormatOriginRecordEx> formatOriginRecordExList = new ArrayList<>();
        if(type == 3){
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//?????????
                        }
                        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();
                        HSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatOriginRecordEx.setEnterprise(enterpriseMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        formatOriginRecordEx.setArea(areaMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        formatOriginRecordEx.setOriginType(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        formatOriginRecordEx.setRecordTime(ExcalUtils.handleDateHSSF(row.getCell(2)));
                        formatOriginRecordEx.setOriginName(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        formatOriginRecordEx.setOriginTypeEx(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        formatOriginRecordEx.setProducer(ExcalUtils.handleStringHSSF(row.getCell(5)));
                        formatOriginRecordEx.setBrand(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        formatOriginRecordEx.setNetContent(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        formatOriginRecordEx.setProduceTime(ExcalUtils.handleDateHSSF(row.getCell(8)));
                        formatOriginRecordEx.setKeepTime(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        formatOriginRecordEx.setDeadTime(ExcalUtils.handleDateHSSF(row.getCell(10)));
                        formatOriginRecordEx.setGoodsIn(ExcalUtils.handleFloatHSSF(row.getCell(11)));
                        formatOriginRecordEx.setGoodsType(ExcalUtils.handleStringHSSF(row.getCell(12)));
                        formatOriginRecordEx.setSupplier(ExcalUtils.handleStringHSSF(row.getCell(13)));
                        formatOriginRecordEx.setSupplierType(ExcalUtils.handleStringHSSF(row.getCell(14)));
                        formatOriginRecordEx.setGoodsOut(ExcalUtils.handleFloatHSSF(row.getCell(11)));
                        formatOriginRecordEx.setGoods(ExcalUtils.handleFloatHSSF(row.getCell(11)));
                        formatOriginRecordEx.setState(ExcalUtils.handleStringHSSF(row.getCell(15)));
                        formatOriginRecordEx.setPerson(ExcalUtils.handleStringHSSF(row.getCell(16)));
                        formatOriginRecordEx.setDocument("");
                        formatOriginRecordEx.setOperator("?????????");
                        formatOriginRecordEx.setOperatorIp("123.123.123");
                        formatOriginRecordEx.setOperatorTime(new Date());
                        if (formatOriginRecordEx.getEnterprise()!=null||formatOriginRecordEx.getArea()!=null||!formatOriginRecordEx.getOriginType().isEmpty())
                        {
                            formatOriginRecordExList.add(formatOriginRecordEx);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == 7){
            try {
                int a=3;
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//?????????
                        }
                        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();
                        XSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatOriginRecordEx.setEnterprise(enterpriseMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        formatOriginRecordEx.setArea(areaMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        formatOriginRecordEx.setOriginType(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        formatOriginRecordEx.setRecordTime(ExcalUtils.handleDateXSSF(row.getCell(2)));
                        formatOriginRecordEx.setOriginName(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        formatOriginRecordEx.setOriginTypeEx(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        formatOriginRecordEx.setProducer(ExcalUtils.handleStringXSSF(row.getCell(5)));
                        formatOriginRecordEx.setBrand(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        formatOriginRecordEx.setNetContent(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        formatOriginRecordEx.setProduceTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                        formatOriginRecordEx.setKeepTime(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatOriginRecordEx.setDeadTime(ExcalUtils.handleDateXSSF(row.getCell(10)));
                        formatOriginRecordEx.setGoodsIn(ExcalUtils.handleFloatXSSF(row.getCell(11)));
                        formatOriginRecordEx.setGoodsType(ExcalUtils.handleStringXSSF(row.getCell(12)));
                        formatOriginRecordEx.setSupplier(ExcalUtils.handleStringXSSF(row.getCell(13)));
                        formatOriginRecordEx.setSupplierType(ExcalUtils.handleStringXSSF(row.getCell(14)));
                        formatOriginRecordEx.setGoodsOut(ExcalUtils.handleFloatXSSF(row.getCell(11)));
                        formatOriginRecordEx.setGoods(ExcalUtils.handleFloatXSSF(row.getCell(11)));
                        formatOriginRecordEx.setState(ExcalUtils.handleStringXSSF(row.getCell(15)));
                        formatOriginRecordEx.setPerson(ExcalUtils.handleStringXSSF(row.getCell(16)));
                        formatOriginRecordEx.setDocument("");
                        formatOriginRecordEx.setOperator("?????????");
                        formatOriginRecordEx.setOperatorIp("123.123.123");
                        formatOriginRecordEx.setOperatorTime(new Date());
                        if (formatOriginRecordEx.getEnterprise()!=null||formatOriginRecordEx.getArea()!=null||!formatOriginRecordEx.getOriginType().isEmpty())
                        {
                            formatOriginRecordExList.add(formatOriginRecordEx);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        formatOriginRecordExMapper.batchInsertEx(formatOriginRecordExList);
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type,SysUser sysUser) {

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"???????????????");
        }

        if(type == 3){
            try {
                Map<String,String> errorMap = new HashMap<>();
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//?????????
                        }
                        HSSFRow row = sheet.getRow(j);
                        if (row==null){
                            break;
                        }
                        int b = j+1;
                        if (row.getCell(0).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(1).getCellType()!= CellType.NUMERIC){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(2).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(3).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(4)!=null&&row.getCell(4).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"????????????","??????????????????");
                        }
                        if (row.getCell(5)!=null&&row.getCell(5).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"?????????","??????????????????");
                        }
                        if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"??????????????????","??????????????????");
                        }
                        if (row.getCell(7).getCellType()!= CellType.NUMERIC){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(8).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"????????????","??????????????????");
                        }
                        if (row.getCell(9).getCellType()!= CellType.NUMERIC){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(10).getCellType()!= CellType.NUMERIC){
                            errorMap.put("???"+b+"????????????","??????????????????");
                        }
                        if (row.getCell(11).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"???????????????","??????????????????");
                        }
                        if (row.getCell(12).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"????????????","??????????????????");
                        }
                        if (row.getCell(13).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"??????????????????","??????????????????");
                        }
                        if (row.getCell(14).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"?????????????????????","??????????????????");
                        }
                        if (row.getCell(15)!=null&&row.getCell(15).getCellType()!= CellType.STRING){
                            errorMap.put("???"+b+"????????????","??????????????????");
                        }
                    }
                }
                if (!errorMap.isEmpty()){
                    System.out.println(MapToStrUtil.getMapToString(errorMap));
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, MapToStrUtil.getMapToString(errorMap));
                }
                if (!errorMap.isEmpty()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, MapToStrUtil.getMapToString(errorMap));
                }
                    workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == 7){
            try {
                Map<String,String> errorMap1 = new HashMap<>();
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//?????????
                        }
                        XSSFRow row = sheet.getRow(j);
                        if (row==null){
                            break;
                        }
                        int a = j+1;
                        if (row.getCell(0).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if (row.getCell(1).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if (row.getCell(2).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if (row.getCell(3).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if ((row.getCell(4)!=null)&&row.getCell(4).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"????????????","??????????????????");
                        }
                        if (row.getCell(5)!=null&&row.getCell(5).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"?????????","??????????????????");
                        }
                        if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"??????????????????","??????????????????");
                        }
                        if (row.getCell(7).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if (row.getCell(8).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"????????????","??????????????????");
                        }
                        if (row.getCell(9).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if (row.getCell(10).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("???"+a+"????????????","??????????????????");
                        }
                        if (row.getCell(11).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"???????????????","??????????????????");
                        }
                        if (row.getCell(12).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"????????????","??????????????????");
                        }
                        if (row.getCell(13).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"??????????????????","??????????????????");
                        }
                        if (row.getCell(14).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"?????????????????????","??????????????????");
                        }
                        if (row.getCell(15)!=null&&row.getCell(15).getCellType()!= CellType.STRING){
                            errorMap1.put("???"+a+"????????????","??????????????????");
                        }
                    }
                }
                if (!errorMap1.isEmpty()){
                    System.out.println(MapToStrUtil.getMapToString(errorMap1));
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, MapToStrUtil.getMapToString(errorMap1));
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }


        List<FormatOriginRecordEx> formatOriginRecordExList = new ArrayList<>();
        if(type == 3){
            try {
                int a=3;
                System.out.println(a);
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//?????????
                        }
                        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();
                        HSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatOriginRecordEx.setEnterprise(sysUser.getInfoId());
                        formatOriginRecordEx.setArea(supervisionEnterprise.getArea());
                        formatOriginRecordEx.setOriginType(ExcalUtils.handleStringHSSF(row.getCell(0)));
                        formatOriginRecordEx.setRecordTime(ExcalUtils.handleDateHSSF(row.getCell(1)));
                        formatOriginRecordEx.setOriginName(ExcalUtils.handleStringHSSF(row.getCell(2)));
                        formatOriginRecordEx.setOriginTypeEx(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        formatOriginRecordEx.setProducer(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        formatOriginRecordEx.setBrand(ExcalUtils.handleStringHSSF(row.getCell(5)));
                        formatOriginRecordEx.setNetContent(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        formatOriginRecordEx.setProduceTime(ExcalUtils.handleDateHSSF(row.getCell(7)));
                        formatOriginRecordEx.setKeepTime(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        formatOriginRecordEx.setDeadTime(ExcalUtils.handleDateHSSF(row.getCell(9)));
                        formatOriginRecordEx.setGoodsIn(ExcalUtils.handleFloatHSSF(row.getCell(10)));
                        formatOriginRecordEx.setGoodsType(ExcalUtils.handleStringHSSF(row.getCell(11)));
                        formatOriginRecordEx.setSupplier(ExcalUtils.handleStringHSSF(row.getCell(12)));
                        formatOriginRecordEx.setSupplierType(ExcalUtils.handleStringHSSF(row.getCell(13)));
                        formatOriginRecordEx.setGoodsOut(ExcalUtils.handleFloatHSSF(row.getCell(10)));
                        formatOriginRecordEx.setGoods(ExcalUtils.handleFloatHSSF(row.getCell(10)));
                        formatOriginRecordEx.setState(ExcalUtils.handleStringHSSF(row.getCell(14)));
                        formatOriginRecordEx.setPerson(ExcalUtils.handleStringHSSF(row.getCell(15)));
                        formatOriginRecordEx.setDocument("");
                        formatOriginRecordEx.setOperator("?????????");
                        formatOriginRecordEx.setOperatorIp("123.123.123");
                        formatOriginRecordEx.setOperatorTime(new Date());
                        if (!formatOriginRecordEx.getOriginType().isEmpty())
                        {
                            formatOriginRecordExList.add(formatOriginRecordEx);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == 7){
            try {
                int a=7;
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//?????????
                        }
                        FormatOriginRecordEx formatOriginRecordEx = new FormatOriginRecordEx();
                        XSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatOriginRecordEx.setEnterprise(sysUser.getInfoId());
                        formatOriginRecordEx.setArea(supervisionEnterprise.getArea());
                        formatOriginRecordEx.setOriginType(ExcalUtils.handleStringXSSF(row.getCell(0)));
                        formatOriginRecordEx.setRecordTime(ExcalUtils.handleDateXSSF(row.getCell(1)));
                        formatOriginRecordEx.setOriginName(ExcalUtils.handleStringXSSF(row.getCell(2)));
                        formatOriginRecordEx.setOriginTypeEx(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        formatOriginRecordEx.setProducer(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        formatOriginRecordEx.setBrand(ExcalUtils.handleStringXSSF(row.getCell(5)));
                        formatOriginRecordEx.setNetContent(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        formatOriginRecordEx.setProduceTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                        formatOriginRecordEx.setKeepTime(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        formatOriginRecordEx.setDeadTime(ExcalUtils.handleDateXSSF(row.getCell(9)));
                        formatOriginRecordEx.setGoodsIn(ExcalUtils.handleFloatXSSF(row.getCell(10)));
                        formatOriginRecordEx.setGoodsType(ExcalUtils.handleStringXSSF(row.getCell(11)));
                        formatOriginRecordEx.setSupplier(ExcalUtils.handleStringXSSF(row.getCell(12)));
                        formatOriginRecordEx.setSupplierType(ExcalUtils.handleStringXSSF(row.getCell(13)));
                        formatOriginRecordEx.setGoodsOut(ExcalUtils.handleFloatXSSF(row.getCell(10)));
                        formatOriginRecordEx.setGoods(ExcalUtils.handleFloatXSSF(row.getCell(10)));
                        formatOriginRecordEx.setState(ExcalUtils.handleStringXSSF(row.getCell(14)));
                        formatOriginRecordEx.setPerson(ExcalUtils.handleStringXSSF(row.getCell(15)));
                        formatOriginRecordEx.setDocument("");
                        formatOriginRecordEx.setOperator("?????????");
                        formatOriginRecordEx.setOperatorIp("123.123.123");
                        formatOriginRecordEx.setOperatorTime(new Date());
                        if (!formatOriginRecordEx.getOriginType().isEmpty())
                        {
                            formatOriginRecordExList.add(formatOriginRecordEx);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        formatOriginRecordExMapper.batchInsertEx(formatOriginRecordExList);
    }

}
