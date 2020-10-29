package com.example.upc.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.*;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.DisinfectionSearchParam;
import com.example.upc.controller.searchParam.LeaveSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.FormatLeaveService;
import com.example.upc.util.ExcalUtils;
import com.example.upc.util.MapToStrUtil;
import com.example.upc.util.operateExcel.WasteExcel;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormatLeaveServiceImpl implements FormatLeaveService {
    @Autowired
    FormatLeaveSampleMapper formatLeaveSampleMapper;
    @Autowired
    FormatLeaveCoolCourseMapper formatLeaveCoolCourseMapper;
    @Autowired
    FormatLeaveMainCourseMapper formatLeaveMainCourseMapper;
    @Autowired
    FormatLeaveMainFoodMapper formatLeaveMainFoodMapper;
    @Autowired
    FormatLeaveSoupMapper formatLeaveSoupMapper;
    @Autowired
    FormatLeaveFruitMapper formatLeaveFruitMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, LeaveSearchParam leaveSearchParam) {
        int count= formatLeaveSampleMapper.countListSup(leaveSearchParam);
        if (count > 0) {
            List<FormatLeaveSupParam> list = formatLeaveSampleMapper.getPage(pageQuery,leaveSearchParam);
            PageResult<FormatLeaveSupParam> pageResult = new PageResult<>();
            pageResult.setData(list);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatLeaveSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, LeaveSearchParam leaveSearchParam) {

        int count= formatLeaveSampleMapper.countListEnterprise(id, leaveSearchParam);

        if (count > 0) {
            List<FormatLeaveSample> fdList = formatLeaveSampleMapper.getPageEnterprise(pageQuery, id, leaveSearchParam);
            PageResult<FormatLeaveSample> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }

        PageResult<FormatLeaveSample> pageResult = new PageResult<>();
        return pageResult;

    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, LeaveSearchParam leaveSearchParam) {
        int count= formatLeaveSampleMapper.countListAdmin(leaveSearchParam);
        if (count > 0) {
            List<FormatLeaveSupParam> list = formatLeaveSampleMapper.getPageAdmin(pageQuery,leaveSearchParam);
            PageResult<FormatLeaveSupParam> pageResult = new PageResult<>();
            pageResult.setData(list);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatLeaveSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public CommonReturnType getFormatLeaveSampleByDate(SysUser sysUser,LeaveSearchParam leaveSearchParam,PageQuery pageQuery)
    {
        if(leaveSearchParam.getStart()!=null) {
            leaveSearchParam.setEnd(new Date(leaveSearchParam.getStart().getTime() + (long) 24 * 60 * 60 * 1000));
            return CommonReturnType.create(formatLeaveSampleMapper.getFoodSamplesRecord(sysUser.getInfoId(),leaveSearchParam.getStart(),leaveSearchParam.getEnd(),pageQuery));
        }
        return CommonReturnType.create(formatLeaveSampleMapper.getFoodSamplesRecord(sysUser.getInfoId(),null,null,pageQuery));
    }

    @Override
    public FormatLeaveMiniParam getById(int id) {
        FormatLeaveSample formatLeaveSample= formatLeaveSampleMapper.selectByPrimaryKey(id);
        FormatLeaveMiniParam formatLeaveMiniParam = new FormatLeaveMiniParam();
        BeanUtils.copyProperties(formatLeaveSample,formatLeaveMiniParam);


        List <FormatLeaveMini> list = Lists.newArrayList();

        List<FormatLeaveCoolCourse> list1 = formatLeaveCoolCourseMapper.selectByParentId(formatLeaveSample.getId());
        if(list1.size()>0){
            for(FormatLeaveCoolCourse formatLeaveCoolCourse:list1)
            {
                FormatLeaveMini formatLeaveMini = new FormatLeaveMini();
                BeanUtils.copyProperties(formatLeaveCoolCourse,formatLeaveMini);
                list.add(formatLeaveMini);
            }
        }
        List<FormatLeaveFruit> list2 = formatLeaveFruitMapper.selectByParentId(formatLeaveSample.getId());
        if(list2.size()>0){
            for(FormatLeaveFruit formatLeaveFruit:list2)
            {
                FormatLeaveMini formatLeaveMini = new FormatLeaveMini();
                BeanUtils.copyProperties(formatLeaveFruit,formatLeaveMini);
                list.add(formatLeaveMini);
            }
        }
        List<FormatLeaveMainCourse> list3 = formatLeaveMainCourseMapper.selectByParentId(formatLeaveSample.getId());
        if(list3.size()>0){
            for(FormatLeaveMainCourse formatLeaveMainCourse:list3)
            {
                FormatLeaveMini formatLeaveMini = new FormatLeaveMini();
                BeanUtils.copyProperties(formatLeaveMainCourse,formatLeaveMini);
                list.add(formatLeaveMini);
            }
        }
        List<FormatLeaveMainFood> list4 = formatLeaveMainFoodMapper.selectByParentId(formatLeaveSample.getId());
        if(list4.size()>0){
            for(FormatLeaveMainFood formatLeaveMainFood:list4)
            {
                FormatLeaveMini formatLeaveMini = new FormatLeaveMini();
                BeanUtils.copyProperties(formatLeaveMainFood,formatLeaveMini);
                list.add(formatLeaveMini);
            }
        }
        List<FormatLeaveSoup> list5 = formatLeaveSoupMapper.selectByParentId(formatLeaveSample.getId());
        if(list5.size()>0){
            for(FormatLeaveSoup formatLeaveSoup:list5)
            {
                FormatLeaveMini formatLeaveMini = new FormatLeaveMini();
                BeanUtils.copyProperties(formatLeaveSoup,formatLeaveMini);
                list.add(formatLeaveMini);
            }
        }

        formatLeaveMiniParam.setList(list);
        return formatLeaveMiniParam;
    }

    @Override
    @Transactional
    public void miniInsert(String json, SysUser sysUser) {

        FormatLeaveMiniParam formatLeaveMiniParam = JSONObject.parseObject(json,FormatLeaveMiniParam.class);
        FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
        BeanUtils.copyProperties(formatLeaveMiniParam,formatLeaveSample);

        ValidationResult result = validator.validate(formatLeaveSample);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        formatLeaveSample.setUnit(sysUser.getInfoId());
        formatLeaveSample.setArea(supervisionEnterprise.getArea());
        formatLeaveSample.setOperatorIp("124.124.124");
        formatLeaveSample.setOperatorTime(new Date());
        formatLeaveSample.setOperator("zcc");
        formatLeaveSampleMapper.insertSelective(formatLeaveSample);

        if(formatLeaveSample.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        //分列表插
        List<FormatLeaveMini> formatLeaveMiniList = formatLeaveMiniParam.getList();

        formatLeaveMainFoodMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveMainCourseMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveCoolCourseMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveSoupMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveFruitMapper.deleteByParentId(formatLeaveSample.getId());

        if(formatLeaveMiniList.size()>0){
                for (FormatLeaveMini formatLeaveMini:formatLeaveMiniList)
                {
                    formatLeaveMini.setOperatorIp("124.124.124");
                    formatLeaveMini.setOperatorTime(new Date());
                    formatLeaveMini.setOperator("zcc");
                    formatLeaveMini.setParentId(formatLeaveSample.getId());

                    switch (formatLeaveMini.getType()){
                            case 1:
                                FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                                BeanUtils.copyProperties(formatLeaveMini,formatLeaveMainFood);
                                formatLeaveMainFoodMapper.insert(formatLeaveMainFood);
                            break;
                            case 2:
                                FormatLeaveMainCourse formatLeaveMainCourse = new FormatLeaveMainCourse();
                                BeanUtils.copyProperties(formatLeaveMini,formatLeaveMainCourse);
                                formatLeaveMainCourseMapper.insert(formatLeaveMainCourse);
                            break;
                            case 3:
                                FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                                BeanUtils.copyProperties(formatLeaveMini,formatLeaveCoolCourse);
                                formatLeaveCoolCourseMapper.insert(formatLeaveCoolCourse);
                            break;
                            case 4:
                                FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                                BeanUtils.copyProperties(formatLeaveMini,formatLeaveSoup);
                                formatLeaveSoupMapper.insert(formatLeaveSoup);
                            break;
                            case 5:
                                FormatLeaveFruit formatLeaveFruit  = new FormatLeaveFruit();
                                BeanUtils.copyProperties(formatLeaveMini,formatLeaveFruit);
                                formatLeaveFruitMapper.insert(formatLeaveFruit);
                            break;
                    }
                }
        }
    }

    @Override
    @Transactional
    public void miniUpdate(String json, SysUser sysUser) {

        FormatLeaveMiniParam formatLeaveMiniParam = JSONObject.parseObject(json,FormatLeaveMiniParam.class);
        FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
        BeanUtils.copyProperties(formatLeaveMiniParam,formatLeaveSample);

        ValidationResult result = validator.validate(formatLeaveSample);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }

        formatLeaveSample.setUnit(sysUser.getInfoId());
        formatLeaveSample.setArea(supervisionEnterprise.getArea());
        formatLeaveSample.setOperatorIp("124.124.124");
        formatLeaveSample.setOperatorTime(new Date());
        formatLeaveSample.setOperator("zcc");
        formatLeaveSampleMapper.updateByPrimaryKey(formatLeaveSample);

        if(formatLeaveSample.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        //分列表插
        List<FormatLeaveMini> formatLeaveMiniList = formatLeaveMiniParam.getList();

        formatLeaveMainFoodMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveMainCourseMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveCoolCourseMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveSoupMapper.deleteByParentId(formatLeaveSample.getId());
        formatLeaveFruitMapper.deleteByParentId(formatLeaveSample.getId());

        if(formatLeaveMiniList.size()>0){
            for (FormatLeaveMini formatLeaveMini:formatLeaveMiniList)
            {
                formatLeaveMini.setOperatorIp("124.124.124");
                formatLeaveMini.setOperatorTime(new Date());
                formatLeaveMini.setOperator("zcc");
                formatLeaveMini.setParentId(formatLeaveSample.getId());

                switch (formatLeaveMini.getType()){
                    case 1:
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveMini,formatLeaveMainFood);
                        formatLeaveMainFoodMapper.insert(formatLeaveMainFood);
                        break;
                    case 2:
                        FormatLeaveMainCourse formatLeaveMainCourse = new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveMini,formatLeaveMainCourse);
                        formatLeaveMainCourseMapper.insert(formatLeaveMainCourse);
                        break;
                    case 3:
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveMini,formatLeaveCoolCourse);
                        formatLeaveCoolCourseMapper.insert(formatLeaveCoolCourse);
                        break;
                    case 4:
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveMini,formatLeaveSoup);
                        formatLeaveSoupMapper.insert(formatLeaveSoup);
                        break;
                    case 5:
                        FormatLeaveFruit formatLeaveFruit  = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveMini,formatLeaveFruit);
                        formatLeaveFruitMapper.insert(formatLeaveFruit);
                        break;
                }
            }
        }
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {

        FormatLeaveParam formatLeaveParam = JSONObject.parseObject(json,FormatLeaveParam.class);
        FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
        BeanUtils.copyProperties(formatLeaveParam,formatLeaveSample);

        ValidationResult result = validator.validate(formatLeaveSample);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }
        formatLeaveSample.setUnit(sysUser.getInfoId());
        formatLeaveSample.setArea(supervisionEnterprise.getArea());
        formatLeaveSample.setOperatorIp("124.124.124");
        formatLeaveSample.setOperatorTime(new Date());
        formatLeaveSample.setOperator("zcc");
        formatLeaveSampleMapper.insertSelective(formatLeaveSample);
        if(formatLeaveSample.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        //分列表插

        formatLeaveCoolCourseMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveCoolCourse> list1 = formatLeaveParam.getList1();
        if(list1.size()>0){
            formatLeaveCoolCourseMapper.batchInsert(list1.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveFruitMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveFruit> list2 = formatLeaveParam.getList2();
        if(list2.size()>0){
            formatLeaveFruitMapper.batchInsert(list2.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveMainCourseMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveMainCourse> list3 = formatLeaveParam.getList3();
        if(list3.size()>0){
            formatLeaveMainCourseMapper.batchInsert(list3.stream().map((list)->{
                list.setParentId(formatLeaveSample.getId());

                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");

                return list;}).collect(Collectors.toList()));
        }

        formatLeaveMainFoodMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveMainFood> list4 = formatLeaveParam.getList4();
        if(list4.size()>0){
            formatLeaveMainFoodMapper.batchInsert(list4.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveSoupMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveSoup> list5 = formatLeaveParam.getList5();
        if(list5.size()>0){
            formatLeaveSoupMapper.batchInsert(list5.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void update(String json, SysUser sysUser) {

        FormatLeaveParam formatLeaveParam = JSONObject.parseObject(json,FormatLeaveParam.class);
        FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
        BeanUtils.copyProperties(formatLeaveParam,formatLeaveSample);

        ValidationResult result = validator.validate(formatLeaveSample);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        FormatLeaveSample before = formatLeaveSampleMapper.selectByPrimaryKey(formatLeaveSample.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新企业不存在");
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
        }
        formatLeaveSample.setUnit(sysUser.getInfoId());
        formatLeaveSample.setArea(supervisionEnterprise.getArea());
        formatLeaveSample.setOperatorIp("124.124.124");
        formatLeaveSample.setOperatorTime(new Date());
        formatLeaveSample.setOperator("zcc");

        //分列表插

        formatLeaveCoolCourseMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveCoolCourse> list1 = formatLeaveParam.getList1();
        if(list1.size()>0){
            formatLeaveCoolCourseMapper.batchInsert(list1.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveFruitMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveFruit> list2 = formatLeaveParam.getList2();
        if(list2.size()>0){
            formatLeaveFruitMapper.batchInsert(list2.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveMainCourseMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveMainCourse> list3 = formatLeaveParam.getList3();
        if(list3.size()>0){
            formatLeaveMainCourseMapper.batchInsert(list3.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveMainFoodMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveMainFood> list4 = formatLeaveParam.getList4();
        if(list4.size()>0){
            formatLeaveMainFoodMapper.batchInsert(list4.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveSoupMapper.deleteByParentId(formatLeaveSample.getId());
        List<FormatLeaveSoup> list5 = formatLeaveParam.getList5();
        if(list5.size()>0){
            formatLeaveSoupMapper.batchInsert(list5.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(formatLeaveSample.getId());
                return list;}).collect(Collectors.toList()));
        }

        formatLeaveSampleMapper.updateByPrimaryKeySelective(formatLeaveSample);
    }

    @Override
    public void delete(int id) {
        FormatLeaveSample formatLeaveSample = formatLeaveSampleMapper.selectByPrimaryKey(id);
        if(formatLeaveSample==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatLeaveSampleMapper.deleteByPrimaryKey(id);
        formatLeaveCoolCourseMapper.deleteByParentId(id);
        formatLeaveFruitMapper.deleteByParentId(id);
        formatLeaveMainCourseMapper.deleteByParentId(id);
        formatLeaveMainFoodMapper.deleteByParentId(id);
        formatLeaveSoupMapper.deleteByParentId(id);
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

        //在这里更换管理员和企业用户的导入的区别：企业名称与id和地区id的更换





        if(type == 3){
            try {
                Map<String,String> errorMap = new HashMap<>();
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet0 = workbook.getSheetAt(0);
                int physicalNumberOfRows0 = sheet0.getPhysicalNumberOfRows();
                HSSFRow row0 = sheet0.getRow(1);
                if (row0.getCell(0)!=null&&row0.getCell(0).getCellType()!= CellType.STRING){
                    errorMap.put("早餐留样第2行就餐类型","不是文本类型");
                }
                if (row0.getCell(1)!=null&&row0.getCell(1).getCellType()!= CellType.NUMERIC){
                    errorMap.put("早餐留样第2行就餐日期","不是日期类型");
                }
                if (row0.getCell(2)!=null&&row0.getCell(2).getCellType()!= CellType.STRING){
                    errorMap.put("早餐留样第2行事项","不是文本类型");
                }
                if (row0.getCell(3)!=null&&row0.getCell(3).getCellType()!= CellType.STRING){
                    errorMap.put("早餐留样第2行人数","不是文本类型");
                }
                if (row0.getCell(4)!=null&&row0.getCell(4).getCellType()!= CellType.STRING){
                    errorMap.put("早餐留样第2行操作人","不是文本类型");
                }

                for (int j = 0; j < physicalNumberOfRows0; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    HSSFRow row = sheet0.getRow(j);
                    if (row==null){
                        break;
                    }
                    int a = j+1;
                    if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                        errorMap.put("早餐留样第"+a+"行菜名","不是文本类型");
                    }
                    if (row.getCell(7)!=null&&row.getCell(7).getCellType()!= CellType.STRING){
                        errorMap.put("早餐留样第"+a+"行原料","不是文本类型");
                    }
                    if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                        errorMap.put("早餐留样第"+a+"行留样状态","不是文本类型");
                    }
                    if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                        errorMap.put("早餐留样第"+a+"行留样量","不是文本类型");
                    }
                }

                HSSFSheet sheet1 = workbook.getSheetAt(1);
                int physicalNumberOfRows1 = sheet1.getPhysicalNumberOfRows();
                HSSFRow row1 = sheet1.getRow(1);
                if (row1.getCell(0)!=null&&row1.getCell(0).getCellType()!= CellType.STRING){
                    errorMap.put("午餐留样第2行就餐类型","不是文本类型");
                }
                if (row1.getCell(1)!=null&&row1.getCell(1).getCellType()!= CellType.NUMERIC){
                    errorMap.put("午餐留样第2行就餐日期","不是日期类型");
                }
                if (row1.getCell(2)!=null&&row1.getCell(2).getCellType()!= CellType.STRING){
                    errorMap.put("午餐留样第2行事项","不是文本类型");
                }
                if (row1.getCell(3)!=null&&row1.getCell(3).getCellType()!= CellType.STRING){
                    errorMap.put("午餐留样第2行人数","不是文本类型");
                }
                if (row1.getCell(4)!=null&&row1.getCell(4).getCellType()!= CellType.STRING){
                    errorMap.put("午餐留样第2行操作人","不是文本类型");
                }

                for (int j = 0; j < physicalNumberOfRows1; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    HSSFRow row = sheet0.getRow(j);
                    if (row==null){
                        break;
                    }
                    int b = j+1;

                    if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                        errorMap.put("午餐留样第"+b+"行菜名","不是文本类型");
                    }
                    if (row.getCell(7)!=null&&row.getCell(7).getCellType()!= CellType.STRING){
                        errorMap.put("午餐留样第"+b+"行原料","不是文本类型");
                    }
                    if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                        errorMap.put("午餐留样第"+b+"行留样状态","不是文本类型");
                    }
                    if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                        errorMap.put("午餐留样第"+b+"行留样量","不是文本类型");
                    }
                }

                HSSFSheet sheet2 = workbook.getSheetAt(2);
                int physicalNumberOfRows2 = sheet2.getPhysicalNumberOfRows();
                HSSFRow row2 = sheet2.getRow(1);
                if (row2.getCell(0)!=null&&row2.getCell(0).getCellType()!= CellType.STRING){
                    errorMap.put("晚餐留样第2行就餐类型","不是文本类型");
                }
                if (row2.getCell(1)!=null&&row2.getCell(1).getCellType()!= CellType.NUMERIC){
                    errorMap.put("晚餐留样第2行就餐日期","不是日期类型");
                }
                if (row2.getCell(2)!=null&&row2.getCell(2).getCellType()!= CellType.STRING){
                    errorMap.put("晚餐留样第2行事项","不是文本类型");
                }
                if (row2.getCell(3)!=null&&row2.getCell(3).getCellType()!= CellType.STRING){
                    errorMap.put("晚餐留样第2行人数","不是文本类型");
                }
                if (row2.getCell(4)!=null&&row2.getCell(4).getCellType()!= CellType.STRING){
                    errorMap.put("晚餐留样第2行操作人","不是文本类型");
                }

                for (int j = 0; j < physicalNumberOfRows2; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    HSSFRow row = sheet2.getRow(j);
                    if (row==null){
                        break;
                    }
                    int c = j+1;

                    if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                        errorMap.put("晚餐留样第"+c+"行菜名","不是文本类型");
                    }
                    if (row.getCell(7)!=null&&row.getCell(7).getCellType()!= CellType.STRING){
                        errorMap.put("晚餐留样第"+c+"行原料","不是文本类型");
                    }
                    if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                        errorMap.put("晚餐留样第"+c+"行留样状态","不是文本类型");
                    }
                    if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                        errorMap.put("晚餐留样第"+c+"行留样量","不是文本类型");
                    }
                }
                if (!errorMap.isEmpty()){
                    System.out.println(MapToStrUtil.getMapToString(errorMap));
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
                XSSFSheet sheet0 = workbook.getSheetAt(0);
                int physicalNumberOfRows0 = sheet0.getPhysicalNumberOfRows();
                XSSFRow row0 = sheet0.getRow(1);
                if (row0.getCell(0)!=null&&row0.getCell(0).getCellType()!= CellType.STRING){
                    errorMap1.put("早餐留样第2行就餐类型","不是文本类型");
                }
                if (row0.getCell(2)!=null&&row0.getCell(1).getCellType()!= CellType.NUMERIC){
                    errorMap1.put("早餐留样第2行就餐日期","不是日期类型");
                }
                if (row0.getCell(2)!=null&&row0.getCell(2).getCellType()!= CellType.STRING){
                    errorMap1.put("早餐留样第2行事项","不是文本类型");
                }
                if (row0.getCell(3)!=null&&row0.getCell(3).getCellType()!= CellType.STRING){
                    errorMap1.put("早餐留样第2行人数","不是文本类型");
                }
                if (row0.getCell(4)!=null&&row0.getCell(4).getCellType()!= CellType.STRING){
                    errorMap1.put("早餐留样第2行操作人","不是文本类型");
                }

                for (int j = 0; j < physicalNumberOfRows0; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    XSSFRow row = sheet0.getRow(j);
                    if (row==null){
                        break;
                    }
                    int a = j+1;

                    if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                        errorMap1.put("早餐留样第"+a+"行菜名","不是文本类型");
                    }
                    if (row.getCell(7)!=null&&row.getCell(7).getCellType()!= CellType.STRING){
                        errorMap1.put("早餐留样第"+a+"行原料","不是文本类型");
                    }
                    if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                        errorMap1.put("早餐留样第"+a+"行留样状态","不是文本类型");
                    }
                    if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                        errorMap1.put("早餐留样第"+a+"行留样量","不是文本类型");
                    }
                }


                XSSFSheet sheet1 = workbook.getSheetAt(1);
                int physicalNumberOfRows1 = sheet1.getPhysicalNumberOfRows();
                XSSFRow row1 = sheet1.getRow(1);
                if (row1.getCell(0)!=null&&row1.getCell(0).getCellType()!= CellType.STRING){
                    errorMap1.put("午餐留样第2行就餐类型","不是文本类型");
                }
                if (row1.getCell(1)!=null&&row1.getCell(1).getCellType()!= CellType.NUMERIC){
                    errorMap1.put("午餐留样第2行就餐日期","不是日期类型");
                }
                if (row1.getCell(2)!=null&&row1.getCell(2).getCellType()!= CellType.STRING){
                    errorMap1.put("午餐留样第2行事项","不是文本类型");
                }
                if (row1.getCell(3)!=null&&row1.getCell(3).getCellType()!= CellType.STRING){
                    errorMap1.put("午餐留样第2行人数","不是文本类型");
                }
                if (row1.getCell(4)!=null&&row1.getCell(4).getCellType()!= CellType.STRING){
                    errorMap1.put("午餐留样第2行操作人","不是文本类型");
                }

                for (int j = 0; j < physicalNumberOfRows1; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    XSSFRow row = sheet1.getRow(j);
                    if (row==null){
                        break;
                    }
                    int b = j+1;

                    if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                        errorMap1.put("午餐留样第"+b+"行菜名","不是文本类型");
                    }
                    if (row.getCell(7)!=null&&row.getCell(7).getCellType()!= CellType.STRING){
                        errorMap1.put("午餐留样第"+b+"行原料","不是文本类型");
                    }
                    if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                        errorMap1.put("午餐留样第"+b+"行留样状态","不是文本类型");
                    }
                    if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                        errorMap1.put("午餐留样第"+b+"行留样量","不是文本类型");
                    }
                }


                XSSFSheet sheet2 = workbook.getSheetAt(2);
                int physicalNumberOfRows2 = sheet2.getPhysicalNumberOfRows();
                XSSFRow row2 = sheet2.getRow(1);
                if (row2.getCell(0)!=null&&row2.getCell(0).getCellType()!= CellType.STRING){
                    errorMap1.put("晚餐留样第2行就餐类型","不是文本类型");
                }
                if (row2.getCell(1)!=null&&row2.getCell(1).getCellType()!= CellType.NUMERIC){
                    errorMap1.put("晚餐留样第2行就餐日期","不是日期类型");
                }
                if (row2.getCell(2)!=null&&row2.getCell(2).getCellType()!= CellType.STRING){
                    errorMap1.put("晚餐留样第2行事项","不是文本类型");
                }
                if (row2.getCell(3)!=null&&row2.getCell(3).getCellType()!= CellType.STRING){
                    errorMap1.put("晚餐留样第2行人数","不是文本类型");
                }
                if (row2.getCell(4)!=null&&row2.getCell(4).getCellType()!= CellType.STRING){
                    errorMap1.put("晚餐留样第2行操作人","不是文本类型");
                }

                for (int j = 0; j < physicalNumberOfRows2; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    XSSFRow row = sheet2.getRow(j);
                    if (row==null){
                        break;
                    }
                    int c = j+1;

                    if (row.getCell(6)!=null&&row.getCell(6).getCellType()!= CellType.STRING){
                        errorMap1.put("晚餐留样第"+c+"行菜名","不是文本类型");
                    }
                    if (row.getCell(7)!=null&&row.getCell(7).getCellType()!= CellType.STRING){
                        errorMap1.put("晚餐留样第"+c+"行原料","不是文本类型");
                    }
                    if (row.getCell(8)!=null&&row.getCell(8).getCellType()!= CellType.STRING){
                        errorMap1.put("晚餐留样第"+c+"行留样状态","不是文本类型");
                    }
                    if (row.getCell(9)!=null&&row.getCell(9).getCellType()!= CellType.STRING){
                        errorMap1.put("晚餐留样第"+c+"行留样量","不是文本类型");
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


        List<FormatLeaveMainFood> formatLeaveMainFoodList = new ArrayList<>();
        List<FormatLeaveMainCourse> formatLeaveMainCourseList = new ArrayList<>();
        List<FormatLeaveSoup> formatLeaveSoupList = new ArrayList<>();
        List<FormatLeaveFruit> formatLeaveFruitList = new ArrayList<>();
        List<FormatLeaveCoolCourse> formatLeaveCoolCourseList = new ArrayList<>();
        if(type == 3){
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet0 = workbook.getSheetAt(0);
                int physicalNumberOfRows0 = sheet0.getPhysicalNumberOfRows();
                HSSFRow row0 = sheet0.getRow(1);

                FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
                formatLeaveSample.setUnit(supervisionEnterprise.getId());
                formatLeaveSample.setArea(supervisionEnterprise.getArea());
                    formatLeaveSample.setType(ExcalUtils.handleStringHSSF(row0.getCell(0)));
                    formatLeaveSample.setDate(ExcalUtils.handleDateHSSF(row0.getCell(1)));
                    formatLeaveSample.setMeal("早餐");
                    formatLeaveSample.setMatter(ExcalUtils.handleStringHSSF(row0.getCell(2)));
                    formatLeaveSample.setNumber(ExcalUtils.handleStringHSSF(row0.getCell(3)));
                    formatLeaveSample.setPerson(ExcalUtils.handleStringHSSF(row0.getCell(4)));
                    formatLeaveSample.setDocument("");
                    formatLeaveSample.setOperatorIp("124.124.124");
                    formatLeaveSample.setOperatorTime(new Date());
                    formatLeaveSample.setOperator("zcc");
                if (!formatLeaveSample.getType().isEmpty()){
                    formatLeaveSampleMapper.insertSelective(formatLeaveSample);}

                    for (int j = 0; j < physicalNumberOfRows0; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                        HSSFRow row = sheet0.getRow(j);
                        if (row==null){
                            break;
                        }
                        String leaveType = ExcalUtils.handleStringHSSF(row.getCell(5));
                        formatLeaveExParam.setParentId(formatLeaveSample.getId());
                        formatLeaveExParam.setSeq(0);
                        formatLeaveExParam.setName(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        formatLeaveExParam.setMaterial1(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        formatLeaveExParam.setState(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleStringHSSF(row.getCell(9))));
                        formatLeaveExParam.setOperator("操作人");
                        formatLeaveExParam.setOperatorIp("123.123.123");
                        formatLeaveExParam.setOperatorTime(new Date());
                        if (leaveType.equals("主菜"))
                        {
                            FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                            formatLeaveMainFoodList.add(formatLeaveMainFood);
//                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                        }
                        else if (leaveType.equals("主食"))
                        {
                            FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                            formatLeaveMainCourseList.add(formatLeaveMainCourse);
//                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                        }
                        else if (leaveType.equals("汤"))
                        {
                            FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                            formatLeaveSoupList.add(formatLeaveSoup);
//                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                        }
                        else if (leaveType.equals("水果"))
                        {
                            FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                            formatLeaveFruitList.add(formatLeaveFruit);
//                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                        }
                        else if (leaveType.equals("凉菜"))
                        {
                            FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                            formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
//                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                        }
                    }

                HSSFSheet sheet1 = workbook.getSheetAt(1);
                int physicalNumberOfRows1 = sheet1.getPhysicalNumberOfRows();
                HSSFRow row1 = sheet1.getRow(1);
                FormatLeaveSample formatLeaveSample1 = new FormatLeaveSample();
                formatLeaveSample1.setUnit(supervisionEnterprise.getId());
                formatLeaveSample1.setArea(supervisionEnterprise.getArea());
                formatLeaveSample1.setType(ExcalUtils.handleStringHSSF(row1.getCell(0)));
                formatLeaveSample1.setDate(ExcalUtils.handleDateHSSF(row1.getCell(1)));
                formatLeaveSample1.setMeal("午餐");
                formatLeaveSample1.setMatter(ExcalUtils.handleStringHSSF(row1.getCell(2)));
                formatLeaveSample1.setNumber(ExcalUtils.handleStringHSSF(row1.getCell(3)));
                formatLeaveSample1.setPerson(ExcalUtils.handleStringHSSF(row1.getCell(4)));
                formatLeaveSample1.setDocument("");
                formatLeaveSample1.setOperatorIp("124.124.124");
                formatLeaveSample1.setOperatorTime(new Date());
                formatLeaveSample1.setOperator("zcc");
                if (!formatLeaveSample1.getType().isEmpty()){
                formatLeaveSampleMapper.insertSelective(formatLeaveSample1);}

                for (int j = 0; j < physicalNumberOfRows1; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }

                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    HSSFRow row = sheet0.getRow(j);
                    if (row==null){
                        break;
                    }
                    String leaveType = ExcalUtils.handleStringHSSF(row.getCell(5));
                    formatLeaveExParam.setParentId(formatLeaveSample1.getId());
                    formatLeaveExParam.setSeq(0);
                    formatLeaveExParam.setName(ExcalUtils.handleStringHSSF(row.getCell(6)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringHSSF(row.getCell(7)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringHSSF(row.getCell(8)));
                    formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleStringHSSF(row.getCell(9))));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        formatLeaveMainFoodList.add(formatLeaveMainFood);
//                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }
                    else if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        formatLeaveMainCourseList.add(formatLeaveMainCourse);
//                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }
                    else if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        formatLeaveSoupList.add(formatLeaveSoup);
//                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }
                    else if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        formatLeaveFruitList.add(formatLeaveFruit);
//                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }
                    else if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
//                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }

                HSSFSheet sheet2 = workbook.getSheetAt(2);
                int physicalNumberOfRows2 = sheet2.getPhysicalNumberOfRows();
                HSSFRow row2 = sheet2.getRow(1);
                FormatLeaveSample formatLeaveSample2 = new FormatLeaveSample();
                formatLeaveSample2.setUnit(supervisionEnterprise.getId());
                formatLeaveSample2.setArea(supervisionEnterprise.getArea());
                formatLeaveSample2.setType(ExcalUtils.handleStringHSSF(row2.getCell(0)));
                formatLeaveSample2.setDate(ExcalUtils.handleDateHSSF(row2.getCell(1)));
                formatLeaveSample2.setMeal("晚餐");
                formatLeaveSample2.setMatter(ExcalUtils.handleStringHSSF(row2.getCell(2)));
                formatLeaveSample2.setNumber(ExcalUtils.handleStringHSSF(row2.getCell(3)));
                formatLeaveSample2.setPerson(ExcalUtils.handleStringHSSF(row2.getCell(4)));
                formatLeaveSample2.setDocument("");
                formatLeaveSample2.setOperatorIp("124.124.124");
                formatLeaveSample2.setOperatorTime(new Date());
                formatLeaveSample2.setOperator("zcc");
                if (!formatLeaveSample2.getType().isEmpty()){
                formatLeaveSampleMapper.insertSelective(formatLeaveSample2);}

                for (int j = 0; j < physicalNumberOfRows2; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }

                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    HSSFRow row = sheet2.getRow(j);
                    if (row==null){
                        break;
                    }
                    String leaveType = ExcalUtils.handleStringHSSF(row.getCell(5));
                    formatLeaveExParam.setParentId(formatLeaveSample2.getId());
                    formatLeaveExParam.setSeq(0);
                    formatLeaveExParam.setName(ExcalUtils.handleStringHSSF(row.getCell(6)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringHSSF(row.getCell(7)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringHSSF(row.getCell(8)));
                    formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleStringHSSF(row.getCell(9))));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        formatLeaveMainFoodList.add(formatLeaveMainFood);
//                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }
                    else if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        formatLeaveMainCourseList.add(formatLeaveMainCourse);
//                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }
                    else if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        formatLeaveSoupList.add(formatLeaveSoup);
//                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }
                    else if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        formatLeaveFruitList.add(formatLeaveFruit);
//                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }
                    else if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
//                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }


                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == 7){
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet0 = workbook.getSheetAt(0);
                int physicalNumberOfRows0 = sheet0.getPhysicalNumberOfRows();
                XSSFRow row0 = sheet0.getRow(1);
                FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
                formatLeaveSample.setUnit(supervisionEnterprise.getId());
                formatLeaveSample.setArea(supervisionEnterprise.getArea());
                    formatLeaveSample.setType(ExcalUtils.handleStringXSSF(row0.getCell(0)));
                    formatLeaveSample.setDate(ExcalUtils.handleDateXSSF(row0.getCell(1)));
                    formatLeaveSample.setMeal("早餐");
                    formatLeaveSample.setMatter(ExcalUtils.handleStringXSSF(row0.getCell(2)));
                    formatLeaveSample.setNumber(ExcalUtils.handleStringXSSF(row0.getCell(3)));
                    formatLeaveSample.setPerson(ExcalUtils.handleStringXSSF(row0.getCell(4)));
                    formatLeaveSample.setDocument("");
                    formatLeaveSample.setOperatorIp("124.124.124");
                    formatLeaveSample.setOperatorTime(new Date());
                    formatLeaveSample.setOperator("zcc");
                if (!formatLeaveSample.getType().isEmpty()){
                    formatLeaveSampleMapper.insertSelective(formatLeaveSample);
                }

                    for (int j = 0; j < physicalNumberOfRows0; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                        XSSFRow row = sheet0.getRow(j);
                        if (row==null){
                            break;
                        }
                        String leaveType = ExcalUtils.handleStringXSSF(row.getCell(5));
                        formatLeaveExParam.setParentId(formatLeaveSample.getId());
                        formatLeaveExParam.setSeq(0);
                        formatLeaveExParam.setName(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        formatLeaveExParam.setMaterial1(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        formatLeaveExParam.setState(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        formatLeaveExParam.setNum(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatLeaveExParam.setOperator("操作人");
                        formatLeaveExParam.setOperatorIp("123.123.123");
                        formatLeaveExParam.setOperatorTime(new Date());
                        if (leaveType.equals("主菜"))
                        {
                            FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                            formatLeaveMainFoodList.add(formatLeaveMainFood);
//                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                        }
                        else if (leaveType.equals("主食"))
                        {
                            FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                            formatLeaveMainCourseList.add(formatLeaveMainCourse);
//                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                        }
                        else if (leaveType.equals("汤"))
                        {
                            FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                            formatLeaveSoupList.add(formatLeaveSoup);
//                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                        }
                        else if (leaveType.equals("水果"))
                        {
                            FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                            formatLeaveFruitList.add(formatLeaveFruit);
//                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                        }
                        else if (leaveType.equals("凉菜"))
                        {
                            FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                            formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
//                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                        }
                    }


                XSSFSheet sheet1 = workbook.getSheetAt(1);
                int physicalNumberOfRows1 = sheet1.getPhysicalNumberOfRows();
                XSSFRow row1 = sheet1.getRow(1);
                FormatLeaveSample formatLeaveSample1 = new FormatLeaveSample();
                formatLeaveSample1.setUnit(supervisionEnterprise.getId());
                formatLeaveSample1.setArea(supervisionEnterprise.getArea());
                formatLeaveSample1.setType(ExcalUtils.handleStringXSSF(row1.getCell(0)));
                formatLeaveSample1.setDate(ExcalUtils.handleDateXSSF(row1.getCell(1)));
                formatLeaveSample1.setMeal("午餐");
                formatLeaveSample1.setMatter(ExcalUtils.handleStringXSSF(row1.getCell(2)));
                formatLeaveSample1.setNumber(ExcalUtils.handleStringXSSF(row1.getCell(3)));
                formatLeaveSample1.setPerson(ExcalUtils.handleStringXSSF(row1.getCell(4)));
                formatLeaveSample1.setDocument("");
                formatLeaveSample1.setOperatorIp("124.124.124");
                formatLeaveSample1.setOperatorTime(new Date());
                formatLeaveSample1.setOperator("zcc");
                if (!formatLeaveSample1.getType().isEmpty()){
                formatLeaveSampleMapper.insertSelective(formatLeaveSample1);
                }

                for (int j = 0; j < physicalNumberOfRows1; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    XSSFRow row = sheet1.getRow(j);
                    if (row==null){
                        break;
                    }
                    String leaveType = ExcalUtils.handleStringXSSF(row.getCell(5));
                    formatLeaveExParam.setParentId(formatLeaveSample1.getId());
                    formatLeaveExParam.setSeq(0);
                    formatLeaveExParam.setName(ExcalUtils.handleStringXSSF(row.getCell(6)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringXSSF(row.getCell(7)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringXSSF(row.getCell(8)));
                    formatLeaveExParam.setNum(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        formatLeaveMainFoodList.add(formatLeaveMainFood);
//                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }
                    else if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        formatLeaveMainCourseList.add(formatLeaveMainCourse);
//                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }
                    else if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        formatLeaveSoupList.add(formatLeaveSoup);
//                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }
                    else if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        formatLeaveFruitList.add(formatLeaveFruit);
//                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }
                    else if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
//                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }


                XSSFSheet sheet2 = workbook.getSheetAt(2);
                int physicalNumberOfRows2 = sheet2.getPhysicalNumberOfRows();
                XSSFRow row2 = sheet2.getRow(1);
                FormatLeaveSample formatLeaveSample2 = new FormatLeaveSample();
                formatLeaveSample2.setUnit(supervisionEnterprise.getId());
                formatLeaveSample2.setArea(supervisionEnterprise.getArea());
                formatLeaveSample2.setType(ExcalUtils.handleStringXSSF(row2.getCell(0)));
                formatLeaveSample2.setDate(ExcalUtils.handleDateXSSF(row2.getCell(1)));
                formatLeaveSample2.setMeal("晚餐");
                formatLeaveSample2.setMatter(ExcalUtils.handleStringXSSF(row2.getCell(2)));
                formatLeaveSample2.setNumber(ExcalUtils.handleStringXSSF(row2.getCell(3)));
                formatLeaveSample2.setPerson(ExcalUtils.handleStringXSSF(row2.getCell(4)));
                formatLeaveSample2.setDocument("");
                formatLeaveSample2.setOperatorIp("124.124.124");
                formatLeaveSample2.setOperatorTime(new Date());
                formatLeaveSample2.setOperator("zcc");
                if (!formatLeaveSample2.getType().isEmpty()){
                formatLeaveSampleMapper.insertSelective(formatLeaveSample2);
                }

                for (int j = 0; j < physicalNumberOfRows2; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    XSSFRow row = sheet2.getRow(j);
                    if (row==null){
                        break;
                    }
                    String leaveType = ExcalUtils.handleStringXSSF(row.getCell(5));
                    formatLeaveExParam.setParentId(formatLeaveSample2.getId());
                    formatLeaveExParam.setSeq(0);
                    formatLeaveExParam.setName(ExcalUtils.handleStringXSSF(row.getCell(6)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringXSSF(row.getCell(7)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringXSSF(row.getCell(8)));
                    formatLeaveExParam.setNum(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        formatLeaveMainFoodList.add(formatLeaveMainFood);
//                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }
                    else if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        formatLeaveMainCourseList.add(formatLeaveMainCourse);
//                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }
                    else if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        formatLeaveSoupList.add(formatLeaveSoup);
//                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }
                    else if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        formatLeaveFruitList.add(formatLeaveFruit);
//                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }
                    else if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
//                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }

                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }
        if (formatLeaveMainFoodList.size()>0) {
            formatLeaveMainFoodMapper.batchInsert(formatLeaveMainFoodList);
        }
        if (formatLeaveMainCourseList.size()>0) {
            formatLeaveMainCourseMapper.batchInsert(formatLeaveMainCourseList);
        }
        if (formatLeaveFruitList.size()>0) {
            formatLeaveFruitMapper.batchInsert(formatLeaveFruitList);
        }
        if (formatLeaveSoupList.size()>0) {
            formatLeaveSoupMapper.batchInsert(formatLeaveSoupList);
        }
        if (formatLeaveCoolCourseList.size()>0) {
            formatLeaveCoolCourseMapper.batchInsert(formatLeaveCoolCourseList);
        }
    }


    @Override
    @Transactional
    public void importExcelAdmin(MultipartFile file, Integer type, SysUser sysUser) {

        List<EnterpriseListResult> enterpriseListResultList = supervisionEnterpriseMapper.getAll();
        Map<String,Integer> enterpriseMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {
            enterpriseMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getId());
        }

        Map<String,Integer> areaMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult: enterpriseListResultList)
        {areaMap.put(enterpriseListResult.getEnterpriseName(),enterpriseListResult.getArea());}


        //在这里更换管理员和企业用户的导入的区别：企业名称与id和地区id的更换

        if(type == 3){

            try {
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                HSSFSheet sheet0 = workbook.getSheetAt(0);
                int physicalNumberOfRows0 = sheet0.getPhysicalNumberOfRows();
                HSSFRow row0 = sheet0.getRow(1);
                FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
                    formatLeaveSample.setUnit(enterpriseMap.get(ExcalUtils.handleStringHSSF(row0.getCell(0))));
                    formatLeaveSample.setArea(areaMap.get(ExcalUtils.handleStringHSSF(row0.getCell(0))));
                    formatLeaveSample.setType(ExcalUtils.handleStringHSSF(row0.getCell(1)));
                    formatLeaveSample.setDate(ExcalUtils.handleDateHSSF(row0.getCell(2)));
                    formatLeaveSample.setMeal(ExcalUtils.handleStringHSSF(row0.getCell(3)));
                    formatLeaveSample.setMatter(ExcalUtils.handleStringHSSF(row0.getCell(4)));
                    formatLeaveSample.setNumber(ExcalUtils.handleStringHSSF(row0.getCell(5)));
                    formatLeaveSample.setPerson(ExcalUtils.handleStringHSSF(row0.getCell(6)));
                    formatLeaveSample.setDocument("");
                    formatLeaveSample.setOperatorIp("124.124.124");
                    formatLeaveSample.setOperatorTime(new Date());
                    formatLeaveSample.setOperator("zcc");
                    formatLeaveSampleMapper.insertSelective(formatLeaveSample);

                    for (int j = 0; j < physicalNumberOfRows0; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                        HSSFRow row = sheet0.getRow(j);
                        String leaveType = ExcalUtils.handleStringHSSF(row.getCell(7));
                        formatLeaveExParam.setParentId(formatLeaveSample.getId());
                        formatLeaveExParam.setSeq(ExcalUtils.handleIntegerHSSF(row.getCell(8)));
                        formatLeaveExParam.setName(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        formatLeaveExParam.setMaterial1(ExcalUtils.handleStringHSSF(row.getCell(10)));
                        formatLeaveExParam.setState(ExcalUtils.handleStringHSSF(row.getCell(11)));
                        formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleStringHSSF(row.getCell(12))));
                        formatLeaveExParam.setOperator("操作人");
                        formatLeaveExParam.setOperatorIp("123.123.123");
                        formatLeaveExParam.setOperatorTime(new Date());
                        if (leaveType.equals("主菜"))
                        {
                            FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                            //formatLeaveMainFoodList.add(formatLeaveMainFood);
                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                        }if (leaveType.equals("主食"))
                        {
                            FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                            //formatLeaveMainCourseList.add(formatLeaveMainCourse);
                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                        }if (leaveType.equals("汤"))
                        {
                            FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                            //formatLeaveSoupList.add(formatLeaveSoup);
                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                        }if (leaveType.equals("水果"))
                        {
                            FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                            //formatLeaveFruitList.add(formatLeaveFruit);
                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                        }if (leaveType.equals("凉菜"))
                        {
                            FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                            //formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                        }
                    }

                HSSFSheet sheet1 = workbook.getSheetAt(1);
                int physicalNumberOfRows1 = sheet1.getPhysicalNumberOfRows();
                HSSFRow row1 = sheet1.getRow(1);
                FormatLeaveSample formatLeaveSample1 = new FormatLeaveSample();
                formatLeaveSample1.setUnit(enterpriseMap.get(ExcalUtils.handleStringHSSF(row1.getCell(0))));
                formatLeaveSample1.setArea(areaMap.get(ExcalUtils.handleStringHSSF(row1.getCell(0))));
                formatLeaveSample1.setType(ExcalUtils.handleStringHSSF(row1.getCell(1)));
                formatLeaveSample1.setDate(ExcalUtils.handleDateHSSF(row1.getCell(2)));
                formatLeaveSample1.setMeal(ExcalUtils.handleStringHSSF(row1.getCell(3)));
                formatLeaveSample1.setMatter(ExcalUtils.handleStringHSSF(row1.getCell(4)));
                formatLeaveSample1.setNumber(ExcalUtils.handleStringHSSF(row1.getCell(5)));
                formatLeaveSample1.setPerson(ExcalUtils.handleStringHSSF(row1.getCell(6)));
                formatLeaveSample1.setDocument("");
                formatLeaveSample1.setOperatorIp("124.124.124");
                formatLeaveSample1.setOperatorTime(new Date());
                formatLeaveSample1.setOperator("zcc");
                formatLeaveSampleMapper.insertSelective(formatLeaveSample1);

                for (int j = 0; j < physicalNumberOfRows1; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    HSSFRow row = sheet1.getRow(j);
                    String leaveType = ExcalUtils.handleStringHSSF(row.getCell(7));
                    formatLeaveExParam.setParentId(formatLeaveSample1.getId());
                    formatLeaveExParam.setSeq(ExcalUtils.handleIntegerHSSF(row.getCell(8)));
                    formatLeaveExParam.setName(ExcalUtils.handleStringHSSF(row.getCell(9)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringHSSF(row.getCell(10)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringHSSF(row.getCell(11)));
                    formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleStringHSSF(row.getCell(12))));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        //formatLeaveMainFoodList.add(formatLeaveMainFood);
                        formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        //formatLeaveMainCourseList.add(formatLeaveMainCourse);
                        formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        //formatLeaveSoupList.add(formatLeaveSoup);
                        formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        //formatLeaveFruitList.add(formatLeaveFruit);
                        formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        //formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
                        formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }


                HSSFSheet sheet2 = workbook.getSheetAt(2);
                int physicalNumberOfRows2 = sheet2.getPhysicalNumberOfRows();
                HSSFRow row2 = sheet0.getRow(1);
                FormatLeaveSample formatLeaveSample2 = new FormatLeaveSample();
                formatLeaveSample2.setUnit(enterpriseMap.get(ExcalUtils.handleStringHSSF(row2.getCell(0))));
                formatLeaveSample2.setArea(areaMap.get(ExcalUtils.handleStringHSSF(row2.getCell(0))));
                formatLeaveSample2.setType(ExcalUtils.handleStringHSSF(row2.getCell(1)));
                formatLeaveSample2.setDate(ExcalUtils.handleDateHSSF(row2.getCell(2)));
                formatLeaveSample2.setMeal(ExcalUtils.handleStringHSSF(row2.getCell(3)));
                formatLeaveSample2.setMatter(ExcalUtils.handleStringHSSF(row2.getCell(4)));
                formatLeaveSample2.setNumber(ExcalUtils.handleStringHSSF(row2.getCell(5)));
                formatLeaveSample2.setPerson(ExcalUtils.handleStringHSSF(row2.getCell(6)));
                formatLeaveSample2.setDocument("");
                formatLeaveSample2.setOperatorIp("124.124.124");
                formatLeaveSample2.setOperatorTime(new Date());
                formatLeaveSample2.setOperator("zcc");
                formatLeaveSampleMapper.insertSelective(formatLeaveSample2);

                for (int j = 0; j < physicalNumberOfRows2; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    HSSFRow row = sheet2.getRow(j);
                    String leaveType = ExcalUtils.handleStringHSSF(row.getCell(7));
                    formatLeaveExParam.setParentId(formatLeaveSample2.getId());
                    formatLeaveExParam.setSeq(ExcalUtils.handleIntegerHSSF(row.getCell(8)));
                    formatLeaveExParam.setName(ExcalUtils.handleStringHSSF(row.getCell(9)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringHSSF(row.getCell(10)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringHSSF(row.getCell(11)));
                    formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleStringHSSF(row.getCell(12))));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        //formatLeaveMainFoodList.add(formatLeaveMainFood);
                        formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        //formatLeaveMainCourseList.add(formatLeaveMainCourse);
                        formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        //formatLeaveSoupList.add(formatLeaveSoup);
                        formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        //formatLeaveFruitList.add(formatLeaveFruit);
                        formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        //formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
                        formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }

                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type == 7){
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet0 = workbook.getSheetAt(0);
                int physicalNumberOfRows0 = sheet0.getPhysicalNumberOfRows();
                XSSFRow row0 = sheet0.getRow(0);
                FormatLeaveSample formatLeaveSample = new FormatLeaveSample();
                    formatLeaveSample.setUnit(enterpriseMap.get(ExcalUtils.handleStringXSSF(row0.getCell(0))));
                    formatLeaveSample.setArea(areaMap.get(ExcalUtils.handleStringXSSF(row0.getCell(0))));
                    formatLeaveSample.setType(ExcalUtils.handleStringXSSF(row0.getCell(1)));
                    formatLeaveSample.setDate(ExcalUtils.handleDateXSSF(row0.getCell(2)));
                    formatLeaveSample.setMeal(ExcalUtils.handleStringXSSF(row0.getCell(3)));
                    formatLeaveSample.setMatter(ExcalUtils.handleStringXSSF(row0.getCell(4)));
                    formatLeaveSample.setNumber(ExcalUtils.handleStringXSSF(row0.getCell(5)));
                    formatLeaveSample.setPerson(ExcalUtils.handleStringXSSF(row0.getCell(6)));
                    formatLeaveSample.setDocument("");
                    formatLeaveSample.setOperatorIp("124.124.124");
                    formatLeaveSample.setOperatorTime(new Date());
                    formatLeaveSample.setOperator("zcc");
                    formatLeaveSampleMapper.insertSelective(formatLeaveSample);

                    for (int j = 0; j < physicalNumberOfRows0; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                        XSSFRow row = sheet0.getRow(j);
                        String leaveType = ExcalUtils.handleStringXSSF(row.getCell(7));
                        formatLeaveExParam.setParentId(formatLeaveSample.getId());
                        formatLeaveExParam.setSeq(ExcalUtils.handleIntegerXSSF(row.getCell(8)));
                        formatLeaveExParam.setName(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        formatLeaveExParam.setMaterial1(ExcalUtils.handleStringXSSF(row.getCell(10)));
                        formatLeaveExParam.setState(ExcalUtils.handleStringXSSF(row.getCell(11)));
                        formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleIntegerXSSF(row.getCell(12))));
                        formatLeaveExParam.setOperator("操作人");
                        formatLeaveExParam.setOperatorIp("123.123.123");
                        formatLeaveExParam.setOperatorTime(new Date());
                        if (leaveType.equals("主菜"))
                        {
                            FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                            //formatLeaveMainFoodList.add(formatLeaveMainFood);
                            formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                        }if (leaveType.equals("主食"))
                        {
                            FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                            //formatLeaveMainCourseList.add(formatLeaveMainCourse);
                            formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                        }if (leaveType.equals("汤"))
                        {
                            FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                            //formatLeaveSoupList.add(formatLeaveSoup);
                            formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                        }if (leaveType.equals("水果"))
                        {
                            FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                            //formatLeaveFruitList.add(formatLeaveFruit);
                            formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                        }if (leaveType.equals("凉菜"))
                        {
                            FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                            BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                            //formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
                            formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                        }
                    }


                XSSFSheet sheet1 = workbook.getSheetAt(1);
                int physicalNumberOfRows1 = sheet1.getPhysicalNumberOfRows();
                XSSFRow row1 = sheet1.getRow(0);
                FormatLeaveSample formatLeaveSample1 = new FormatLeaveSample();
                formatLeaveSample1.setUnit(enterpriseMap.get(ExcalUtils.handleStringXSSF(row1.getCell(0))));
                formatLeaveSample1.setArea(areaMap.get(ExcalUtils.handleStringXSSF(row1.getCell(0))));
                formatLeaveSample1.setType(ExcalUtils.handleStringXSSF(row1.getCell(1)));
                formatLeaveSample1.setDate(ExcalUtils.handleDateXSSF(row1.getCell(2)));
                formatLeaveSample1.setMeal(ExcalUtils.handleStringXSSF(row1.getCell(3)));
                formatLeaveSample1.setMatter(ExcalUtils.handleStringXSSF(row1.getCell(4)));
                formatLeaveSample1.setNumber(ExcalUtils.handleStringXSSF(row1.getCell(5)));
                formatLeaveSample1.setPerson(ExcalUtils.handleStringXSSF(row1.getCell(6)));
                formatLeaveSample1.setDocument("");
                formatLeaveSample1.setOperatorIp("124.124.124");
                formatLeaveSample1.setOperatorTime(new Date());
                formatLeaveSample1.setOperator("zcc");
                formatLeaveSampleMapper.insertSelective(formatLeaveSample1);

                for (int j = 0; j < physicalNumberOfRows1; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    XSSFRow row = sheet0.getRow(j);
                    String leaveType = ExcalUtils.handleStringXSSF(row.getCell(7));
                    formatLeaveExParam.setParentId(formatLeaveSample1.getId());
                    formatLeaveExParam.setSeq(ExcalUtils.handleIntegerXSSF(row.getCell(8)));
                    formatLeaveExParam.setName(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleIntegerXSSF(row.getCell(12))));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        //formatLeaveMainFoodList.add(formatLeaveMainFood);
                        formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        //formatLeaveMainCourseList.add(formatLeaveMainCourse);
                        formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        //formatLeaveSoupList.add(formatLeaveSoup);
                        formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        //formatLeaveFruitList.add(formatLeaveFruit);
                        formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        //formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
                        formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }


                XSSFSheet sheet2 = workbook.getSheetAt(2);
                int physicalNumberOfRows2 = sheet2.getPhysicalNumberOfRows();
                XSSFRow row2 = sheet1.getRow(0);
                FormatLeaveSample formatLeaveSample2 = new FormatLeaveSample();
                formatLeaveSample2.setUnit(enterpriseMap.get(ExcalUtils.handleStringXSSF(row2.getCell(0))));
                formatLeaveSample2.setArea(areaMap.get(ExcalUtils.handleStringXSSF(row2.getCell(0))));
                formatLeaveSample2.setType(ExcalUtils.handleStringXSSF(row2.getCell(1)));
                formatLeaveSample2.setDate(ExcalUtils.handleDateXSSF(row2.getCell(2)));
                formatLeaveSample2.setMeal(ExcalUtils.handleStringXSSF(row2.getCell(3)));
                formatLeaveSample2.setMatter(ExcalUtils.handleStringXSSF(row2.getCell(4)));
                formatLeaveSample2.setNumber(ExcalUtils.handleStringXSSF(row2.getCell(5)));
                formatLeaveSample2.setPerson(ExcalUtils.handleStringXSSF(row2.getCell(6)));
                formatLeaveSample2.setDocument("");
                formatLeaveSample2.setOperatorIp("124.124.124");
                formatLeaveSample2.setOperatorTime(new Date());
                formatLeaveSample2.setOperator("zcc");
                formatLeaveSampleMapper.insertSelective(formatLeaveSample2);

                for (int j = 0; j < physicalNumberOfRows2; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    FormatLeaveExParam formatLeaveExParam = new FormatLeaveExParam();
                    XSSFRow row = sheet0.getRow(j);
                    String leaveType = ExcalUtils.handleStringXSSF(row.getCell(7));
                    formatLeaveExParam.setParentId(formatLeaveSample2.getId());
                    formatLeaveExParam.setSeq(ExcalUtils.handleIntegerXSSF(row.getCell(8)));
                    formatLeaveExParam.setName(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    formatLeaveExParam.setMaterial1(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    formatLeaveExParam.setState(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    formatLeaveExParam.setNum(String.valueOf(ExcalUtils.handleIntegerXSSF(row.getCell(12))));
                    formatLeaveExParam.setOperator("操作人");
                    formatLeaveExParam.setOperatorIp("123.123.123");
                    formatLeaveExParam.setOperatorTime(new Date());
                    if (leaveType.equals("主菜"))
                    {
                        FormatLeaveMainFood formatLeaveMainFood = new FormatLeaveMainFood();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainFood);
                        //formatLeaveMainFoodList.add(formatLeaveMainFood);
                        formatLeaveMainFoodMapper.insertSelective(formatLeaveMainFood);
                    }if (leaveType.equals("主食"))
                    {
                        FormatLeaveMainCourse formatLeaveMainCourse= new FormatLeaveMainCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveMainCourse);
                        //formatLeaveMainCourseList.add(formatLeaveMainCourse);
                        formatLeaveMainCourseMapper.insertSelective(formatLeaveMainCourse);
                    }if (leaveType.equals("汤"))
                    {
                        FormatLeaveSoup formatLeaveSoup = new FormatLeaveSoup();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveSoup);
                        //formatLeaveSoupList.add(formatLeaveSoup);
                        formatLeaveSoupMapper.insertSelective(formatLeaveSoup);
                    }if (leaveType.equals("水果"))
                    {
                        FormatLeaveFruit formatLeaveFruit = new FormatLeaveFruit();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveFruit);
                        //formatLeaveFruitList.add(formatLeaveFruit);
                        formatLeaveFruitMapper.insertSelective(formatLeaveFruit);
                    }if (leaveType.equals("凉菜"))
                    {
                        FormatLeaveCoolCourse formatLeaveCoolCourse = new FormatLeaveCoolCourse();
                        BeanUtils.copyProperties(formatLeaveExParam,formatLeaveCoolCourse);
                        //formatLeaveCoolCourseList.add(formatLeaveCoolCourse);
                        formatLeaveCoolCourseMapper.insertSelective(formatLeaveCoolCourse);
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }

    }

    /**
     * 小程序专用serviceImpl
     */

    @Override
    public List<Object> getFoodSamplesRecord(int enterpriseId,Date startDate) {
        Date endDate = new Date();
        endDate.setTime(startDate.getTime()+86399999);
        PageQuery pageQuery = new PageQuery();
        List<FormatLeaveSample> searchList = formatLeaveSampleMapper.getFoodSamplesRecord(enterpriseId, startDate, endDate,pageQuery);
        List<Object> resultList = new LinkedList<>();
        for (int i=0;i<searchList.size();i++){
            Map<String,Object> tempItem = new LinkedHashMap<>();
            int id = searchList.get(i).getId();
            tempItem.put("id",id);  // 食品留样id
            tempItem.put("meal",searchList.get(i).getMeal());
            tempItem.put("number",searchList.get(i).getNumber());
            tempItem.put("person",searchList.get(i).getPerson());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tempItem.put("date",simpleDateFormat.format(searchList.get(i).getDate()));
            tempItem.put("matter",searchList.get(i).getMatter());
            tempItem.put("type",searchList.get(i).getType());
            tempItem.put("list1",formatLeaveCoolCourseMapper.selectByParentId(id));
            tempItem.put("list2",formatLeaveFruitMapper.selectByParentId(id));
            tempItem.put("list3",formatLeaveMainCourseMapper.selectByParentId(id));
            tempItem.put("list4",formatLeaveMainFoodMapper.selectByParentId(id));
            tempItem.put("list5",formatLeaveSoupMapper.selectByParentId(id));
            tempItem.put("description","list1～5：凉菜类、水果辅食类、主食类、热菜类、汤/奶类；matter、type是事项、就餐类型，web端必填内容");

            resultList.add(tempItem);
        }
        return resultList;
    }

    @Override
    public String standingBook (LeaveSearchParam leaveSearchParam, SysUser sysUser) throws IOException {
        List<FormatLeaveExportParam> formatLeaveExportParamList = formatLeaveSampleMapper.getFoodSamplesForExport(sysUser.getInfoId(),leaveSearchParam.getStart(),leaveSearchParam.getEnd());
        List<String[]> data = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (FormatLeaveExportParam item:formatLeaveExportParamList) {
            data.add(new String[]{
                    dateFormat.format(item.getDate()),item.getMeal(),item.getMealType(),item.getNumber(),item.getPerson(),item.getSampleType(),item.getName(),item.getMaterial1(),item.getState(),item.getNum(),""
            });
        }
        String fileName = "食品留样";
        String path = WasteExcel.getXLsx(data,"/template/【导出】食品留样模板.xlsx",fileName,sysUser.getInfoId());
        //下载
        //UploadController.downloadStandingBook(response, fileName,path);
        return path;
    }
}
