package com.example.upc.service.impl;

import com.example.upc.common.*;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.DisinfectionSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dao.FormatDisinfectionMapper;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.FormatDisinfection;
import com.example.upc.dataobject.FormatWaste;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatDisinfectionService;
import com.example.upc.util.ExcalUtils;
import com.example.upc.util.MapToStrUtil;
import com.example.upc.util.operateExcel.WasteExcel;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FormatDisinfectionServiceImpl implements FormatDisinfectionService {
    @Autowired
    FormatDisinfectionMapper formatDisinfectionMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, DisinfectionSearchParam disinfectionSearchParam) {
        int count=formatDisinfectionMapper.countListSup(disinfectionSearchParam);
        if (count > 0) {
            List<FormatDisinfectionSupParam> fdList = formatDisinfectionMapper.getPage(pageQuery, disinfectionSearchParam);
            PageResult<FormatDisinfectionSupParam> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatDisinfectionSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, DisinfectionSearchParam disinfectionSearchParam) {
        int count=formatDisinfectionMapper.countListEnterprise(id, disinfectionSearchParam);
        if (count > 0) {
            List<FormatDisinfection> fdList = formatDisinfectionMapper.getPageEnterprise(pageQuery,id, disinfectionSearchParam);
            PageResult<FormatDisinfection> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatDisinfection> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, DisinfectionSearchParam disinfectionSearchParam) {
        int count=formatDisinfectionMapper.countListAdmin(disinfectionSearchParam);
        if (count > 0) {
            List<FormatDisinfectionSupParam> fdList = formatDisinfectionMapper.getPageAdmin(pageQuery, disinfectionSearchParam);
            PageResult<FormatDisinfectionSupParam> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatDisinfectionSupParam> pageResult = new PageResult<>();
        return pageResult;
    }


    @Override
    @Transactional
    public void insert(FormatDisinfectionParam formatDisinfectionParam, SysUser sysUser) throws InvocationTargetException, IllegalAccessException {

        ValidationResult result = validator.validate(formatDisinfectionParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        FormatDisinfection formatDisinfection1 = new FormatDisinfection();

        formatDisinfection1.setUnit(sysUser.getInfoId());
        formatDisinfection1.setArea(supervisionEnterprise.getArea());
        formatDisinfection1.setCaId(formatDisinfectionParam.getCaId());
        formatDisinfection1.setName(formatDisinfectionParam.getName());
        formatDisinfection1.setAmount(formatDisinfectionParam.getAmount());
        formatDisinfection1.setDate(formatDisinfectionParam.getDate());
        formatDisinfection1.setPerson(formatDisinfectionParam.getPerson());
        formatDisinfection1.setStart1(formatDisinfectionParam.getStart1());
        formatDisinfection1.setStart2(formatDisinfectionParam.getStart2());
        formatDisinfection1.setEnd1(formatDisinfectionParam.getEnd1());
        formatDisinfection1.setEnd2(formatDisinfectionParam.getEnd2());
        formatDisinfection1.setWay(formatDisinfectionParam.getWay());
        formatDisinfection1.setRemark(formatDisinfectionParam.getRemark());
        formatDisinfection1.setOperator("操作人");
        formatDisinfection1.setOperatorIp("124.124.124");
        formatDisinfection1.setOperatorTime(new Date());
        formatDisinfection1.setUnit(sysUser.getInfoId());
        formatDisinfection1.setArea(supervisionEnterprise.getArea());

        formatDisinfectionMapper.insertSelective(formatDisinfection1);
//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
    }
    @Override
    public void delete(FormatDisinfectionParam formatDisinfectionParam) {
        FormatDisinfection formatDisinfection = formatDisinfectionMapper.selectByPrimaryKey(formatDisinfectionParam.getId());
        if(formatDisinfection==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatDisinfectionMapper.deleteByPrimaryKey(formatDisinfectionParam.getId());
    }

    @Override
    @Transactional
    public void update(FormatDisinfectionParam formatDisinfectionParam,SysUser sysUser) throws InvocationTargetException, IllegalAccessException {
        ValidationResult result = validator.validate(formatDisinfectionParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(formatDisinfectionMapper.selectByPrimaryKey(formatDisinfectionParam.getId())==null){
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        FormatDisinfection formatDisinfection1 = new FormatDisinfection();
        formatDisinfection1.setId(formatDisinfectionParam.getId());
        formatDisinfection1.setUnit(sysUser.getInfoId());
        formatDisinfection1.setArea(supervisionEnterprise.getArea());
        formatDisinfection1.setCaId(formatDisinfectionParam.getCaId());
        formatDisinfection1.setName(formatDisinfectionParam.getName());
        formatDisinfection1.setAmount(formatDisinfectionParam.getAmount());
        formatDisinfection1.setDate(formatDisinfectionParam.getDate());
        formatDisinfection1.setPerson(formatDisinfectionParam.getPerson());
        formatDisinfection1.setStart1(formatDisinfectionParam.getStart1());
        formatDisinfection1.setStart2(formatDisinfectionParam.getStart2());
        formatDisinfection1.setEnd1(formatDisinfectionParam.getEnd1());
        formatDisinfection1.setEnd2(formatDisinfectionParam.getEnd2());
        formatDisinfection1.setWay(formatDisinfectionParam.getWay());
        formatDisinfection1.setRemark(formatDisinfectionParam.getRemark());
        formatDisinfection1.setOperator("操作人");
        formatDisinfection1.setOperatorIp("124.124.124");
        formatDisinfection1.setOperatorTime(new Date());
        formatDisinfection1.setUnit(sysUser.getInfoId());
        formatDisinfection1.setArea(supervisionEnterprise.getArea());

        // TODO: sendEmail
        formatDisinfectionMapper.updateByPrimaryKeySelective(formatDisinfection1);
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type, SysUser sysUser) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
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
                            continue;//标题行
                        }
                        HSSFRow row = sheet.getRow(j);
                        if (row==null){
                            break;
                        }
                        int b = j+1;
                         if (row.getCell(0).getCellType()!= CellType.NUMERIC){
                            errorMap.put("第"+b+"行消毒日期","不是日期类型");
                        }
                         if (row.getCell(1).getCellType()!= CellType.STRING){
                            errorMap.put("第"+b+"行餐具名称","不是文本类型");
                        }
                         if (row.getCell(2).getCellType()!= CellType.NUMERIC){
                            errorMap.put("第"+b+"行餐具数量","不是数字类型");
                        }
                         if (row.getCell(3).getCellType()!= CellType.STRING){
                            errorMap.put("第"+b+"行消毒方式","不是文本类型");
                        }
                         if (row.getCell(4).getCellType()!= CellType.NUMERIC){
                            errorMap.put("第"+b+"行消毒开始时间（时）","不是数字类型");
                        }
                         if (row.getCell(5).getCellType()!= CellType.NUMERIC){
                            errorMap.put("第"+b+"行消毒开始时间（分）","不是数字类型");
                        }
                         if (row.getCell(6).getCellType()!= CellType.NUMERIC){
                            errorMap.put("第"+b+"行消毒结束时间（时）","不是数字类型");
                        }
                         if (row.getCell(7).getCellType()!= CellType.NUMERIC){
                            errorMap.put("第"+b+"行消毒结束时间（分）","不是数字类型");
                        }
                         if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                            errorMap.put("第"+b+"行操作人","不是文本类型");
                        }
                         if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                            errorMap.put("第"+b+"行备注","不是文本类型");
                        }
                    }
                }
                if (!errorMap.isEmpty()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, MapToStrUtil.getMapToString(errorMap));                }
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
                            continue;//标题行
                        }
                        XSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        int a = j+1;
                        if (row.getCell(0).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("第"+a+"行消毒日期","不是日期类型");
                        }
                         if (row.getCell(1).getCellType()!= CellType.STRING){
                            errorMap1.put("第"+a+"行餐具名称","不是文本类型");
                        }
                         if (row.getCell(2).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("第"+a+"行餐具数量","不是数字类型");
                        }
                         if (row.getCell(3).getCellType()!= CellType.STRING){
                            errorMap1.put("第"+a+"行消毒方式","不是文本类型");
                        }
                         if (row.getCell(4).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("第"+a+"行消毒开始时间（时）","不是数字类型");
                        }
                         if (row.getCell(5).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("第"+a+"行消毒开始时间（分）","不是数字类型");
                        }
                         if (row.getCell(6).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("第"+a+"行消毒结束时间（时）","不是数字类型");
                        }
                         if (row.getCell(7).getCellType()!= CellType.NUMERIC){
                            errorMap1.put("第"+a+"行消毒结束时间（分）","不是数字类型");
                        }
                         if (row.getCell(8)!=null&&row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                            errorMap1.put("第"+a+"行操作人","不是文本类型");
                        }
                         if (row.getCell(9)!=null&&row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                            errorMap1.put("第"+a+"行备注","不是文本类型");
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
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }

        List<FormatDisinfection> formatDisinfectionList = new ArrayList<>();
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

                        FormatDisinfection formatDisinfection = new FormatDisinfection();
                        HSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatDisinfection.setUnit(sysUser.getInfoId());
                        formatDisinfection.setArea(supervisionEnterprise.getArea());
                        formatDisinfection.setName(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        formatDisinfection.setAmount(ExcalUtils.handleIntegerHSSF(row.getCell(2)));
                        formatDisinfection.setDate(ExcalUtils.handleDateHSSF(row.getCell(0)));
                        formatDisinfection.setPerson(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        formatDisinfection.setStart1(ExcalUtils.handleIntegerHSSF(row.getCell(4)));
                        formatDisinfection.setStart2(ExcalUtils.handleIntegerHSSF(row.getCell(5)));
                        formatDisinfection.setEnd1(ExcalUtils.handleIntegerHSSF(row.getCell(6)));
                        formatDisinfection.setEnd2(ExcalUtils.handleIntegerHSSF(row.getCell(7)));
                        formatDisinfection.setWay(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        formatDisinfection.setRemark(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        formatDisinfection.setOperator("操作人");
                        formatDisinfection.setOperatorIp("123.123.123");
                        formatDisinfection.setOperatorTime(new Date());
                        if (formatDisinfection.getUnit()!=null||formatDisinfection.getArea()!=null)
                        {
                            formatDisinfectionList.add(formatDisinfection);
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
                        FormatDisinfection formatDisinfection = new FormatDisinfection();
                        XSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatDisinfection.setUnit(sysUser.getInfoId());
                        formatDisinfection.setArea(supervisionEnterprise.getArea());
                        formatDisinfection.setName(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        formatDisinfection.setAmount(ExcalUtils.handleIntegerXSSF(row.getCell(2)));
                        formatDisinfection.setDate(ExcalUtils.handleDateXSSF(row.getCell(0)));
                        formatDisinfection.setPerson(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        formatDisinfection.setStart1(ExcalUtils.handleIntegerXSSF(row.getCell(4)));
                        formatDisinfection.setStart2(ExcalUtils.handleIntegerXSSF(row.getCell(5)));
                        formatDisinfection.setEnd1(ExcalUtils.handleIntegerXSSF(row.getCell(6)));
                        formatDisinfection.setEnd2(ExcalUtils.handleIntegerXSSF(row.getCell(7)));
                        formatDisinfection.setWay(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        formatDisinfection.setRemark(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatDisinfection.setOperator("操作人");
                        formatDisinfection.setOperatorIp("123.123.123");
                        formatDisinfection.setOperatorTime(new Date());
                        if (formatDisinfection.getUnit()!=null||formatDisinfection.getArea()!=null)
                        {
                            formatDisinfectionList.add(formatDisinfection);
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
        formatDisinfectionMapper.batchInsertEx(formatDisinfectionList);
    }

    @Override
    @Transactional
    public void importExcelEx(MultipartFile file, Integer type) {
        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
        Map<String,Integer> enterpriseMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());}

        Map<String,Integer> areaMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {areaMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getArea());}

        List<FormatDisinfection> formatDisinfectionList = new ArrayList<>();
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
                        FormatDisinfection formatDisinfection = new FormatDisinfection();
                        HSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatDisinfection.setUnit(enterpriseMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        formatDisinfection.setArea(areaMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        formatDisinfection.setName(ExcalUtils.handleStringHSSF(row.getCell(2)));
                        formatDisinfection.setAmount(ExcalUtils.handleIntegerHSSF(row.getCell(3)));
                        formatDisinfection.setDate(ExcalUtils.handleDateHSSF(row.getCell(1)));
                        formatDisinfection.setPerson(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        formatDisinfection.setStart1(ExcalUtils.handleIntegerHSSF(row.getCell(5)));
                        formatDisinfection.setStart2(ExcalUtils.handleIntegerHSSF(row.getCell(6)));
                        formatDisinfection.setEnd1(ExcalUtils.handleIntegerHSSF(row.getCell(7)));
                        formatDisinfection.setEnd2(ExcalUtils.handleIntegerHSSF(row.getCell(8)));
                        formatDisinfection.setWay(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        formatDisinfection.setRemark(ExcalUtils.handleStringHSSF(row.getCell(10)));
                        formatDisinfection.setOperator("操作人");
                        formatDisinfection.setOperatorIp("123.123.123");
                        formatDisinfection.setOperatorTime(new Date());
                        if (formatDisinfection.getUnit()!=null||formatDisinfection.getArea()!=null)
                        {
                            formatDisinfectionList.add(formatDisinfection);
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
                        FormatDisinfection formatDisinfection = new FormatDisinfection();
                        XSSFRow row = sheet.getRow(j);
                        if (row.getCell(0) ==null && row.getCell(0).getCellType() == CellType.BLANK){
                            break;
                        }
                        formatDisinfection.setUnit(enterpriseMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        formatDisinfection.setArea(areaMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        formatDisinfection.setName(ExcalUtils.handleStringXSSF(row.getCell(2)));
                        formatDisinfection.setAmount(ExcalUtils.handleIntegerXSSF(row.getCell(3)));
                        formatDisinfection.setDate(ExcalUtils.handleDateXSSF(row.getCell(1)));
                        formatDisinfection.setPerson(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatDisinfection.setStart1(ExcalUtils.handleIntegerXSSF(row.getCell(5)));
                        formatDisinfection.setStart2(ExcalUtils.handleIntegerXSSF(row.getCell(6)));
                        formatDisinfection.setEnd1(ExcalUtils.handleIntegerXSSF(row.getCell(7)));
                        formatDisinfection.setEnd2(ExcalUtils.handleIntegerXSSF(row.getCell(8)));
                        formatDisinfection.setWay(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        formatDisinfection.setRemark(ExcalUtils.handleStringXSSF(row.getCell(10)));
                        formatDisinfection.setOperator("操作人");
                        formatDisinfection.setOperatorIp("123.123.123");
                        formatDisinfection.setOperatorTime(new Date());
                        if (formatDisinfection.getUnit()!=null||formatDisinfection.getArea()!=null)
                        {
                            formatDisinfectionList.add(formatDisinfection);
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
        formatDisinfectionMapper.batchInsertEx(formatDisinfectionList);
    }
    /**
     * 小程序专用serviceImpl
     */
    @Override
    public List<FormatDisinfection> getDisinfectionRecord(int enterpriseId,Date startDate) {
        Date endDate = new Date();
        endDate.setTime(startDate.getTime()+86399999);
        List<FormatDisinfection> searchList = formatDisinfectionMapper.getDisinfectionRecord(enterpriseId, startDate, endDate);
        return searchList;
    }

    @Override
    public String standingBook (DisinfectionSearchParam disinfectionSearchParam, SysUser sysUser) throws IOException {
        List<FormatDisinfection> formatDisinfectionList = formatDisinfectionMapper.getDisinfectionRecord(sysUser.getInfoId(),disinfectionSearchParam.getStart(),disinfectionSearchParam.getEnd());
        List<String[]> data = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (FormatDisinfection item:formatDisinfectionList) {
            data.add(new String[]{
                    dateFormat.format(item.getDate()),item.getName(),item.getAmount().toString(),item.getWay(),
                    item.getStart1()<10?("0"+item.getStart1()+" : "+(item.getStart2()<10?"0"+item.getStart2():item.getStart2())):(item.getStart1()+" : "+(item.getStart2()<10?"0"+item.getStart2():item.getStart2())),
                    item.getEnd1()<10?("0"+item.getEnd1()+" : "+(item.getEnd2()<10?"0"+item.getEnd2():item.getEnd2())):(item.getEnd1()+" : "+(item.getEnd2()<10?"0"+item.getEnd2():item.getEnd2())),
                    item.getPerson(),item.getRemark(),""
            });
        }
        String fileName = "清洗消毒";
        String path = WasteExcel.getXLsx(data,"/template/【导出】清洗消毒模板.xlsx",fileName,sysUser.getInfoId());

        return path;
    }

}
