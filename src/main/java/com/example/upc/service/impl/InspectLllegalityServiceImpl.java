package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.InspectLllegalityConfMapper;
import com.example.upc.dao.InspectLllegalityMapper;
import com.example.upc.dataobject.InspectLllegality;
import com.example.upc.dataobject.InspectLllegalityConf;
import com.example.upc.service.InspectLllegalityService;
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
import java.util.*;

/**
 * @author zcc
 * @date 2019/9/13 10:59
 */
@Service
public class InspectLllegalityServiceImpl implements InspectLllegalityService {
    @Autowired
    private InspectLllegalityMapper inspectLllegalityMapper;
    @Autowired
    private InspectLllegalityConfMapper inspectLllegalityConfMapper;
    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=inspectLllegalityMapper.countList();
        if (count > 0) {
            List<InspectLllegality> inspectLllegalityList = inspectLllegalityMapper.getPage(pageQuery);
            PageResult<InspectLllegality> pageResult = new PageResult<>();
            pageResult.setData(inspectLllegalityList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<InspectLllegality> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(InspectLllegality inspectLllegality) {
        inspectLllegality.setOperateIp("124.214.124");
        inspectLllegality.setOperateTime(new Date());
        inspectLllegality.setOperator("操作人");
        inspectLllegalityMapper.insertSelective(inspectLllegality);
    }

    @Override
    @Transactional
    public void update(InspectLllegality inspectLllegality) {
        InspectLllegality before = inspectLllegalityMapper.selectByPrimaryKey(inspectLllegality.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法更新");
        }
        inspectLllegality.setOperateIp("124.214.124");
        inspectLllegality.setOperateTime(new Date());
        inspectLllegality.setOperator("操作人");
        inspectLllegalityMapper.updateByPrimaryKeySelective(inspectLllegality);
    }

    @Override
    public void delete(int id) {
        InspectLllegality before = inspectLllegalityMapper.selectByPrimaryKey(id);
        if(before ==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        inspectLllegalityMapper.deleteByPrimaryKey(id);
    }
    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type) {

        List<InspectLllegalityConf> inspectLllegalityConfList= inspectLllegalityConfMapper.getList();
        Map<String,Integer> inspectLllegalityMap = new HashMap<>();
        for (InspectLllegalityConf inspectLllegalityConf: inspectLllegalityConfList)
        {inspectLllegalityMap.put(inspectLllegalityConf.getName(),inspectLllegalityConf.getId());}


        List<InspectLllegality> inspectLllegalityList = new ArrayList<>();
        if(type == 3){
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                int numberOfSheets = workbook.getNumberOfSheets();
                //搜索工作表
                for (int i = 0; i < numberOfSheets; i++) {
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                    for (int j = 0; j < physicalNumberOfRows; j++) {
                        if (j == 0) {
                            continue;//标题行
                        }
                        InspectLllegality inspectLllegality = new InspectLllegality();
                        HSSFRow row = sheet.getRow(j);
//                        inspectLllegality.setTypeId();
                        //列cell
//                        inspectLllegality.setTypeId(ExcalUtils.handleIntegerHSSF(row.getCell(0)));
                        //键值对value：value，给它什么返回他的另一个值
                        inspectLllegality.setTypeId(inspectLllegalityMap.get(ExcalUtils.handleStringHSSF(row.getCell(0))));
                        inspectLllegality.setActivities(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        inspectLllegality.setRegulations(ExcalUtils.handleStringHSSF(row.getCell(2)));
                        inspectLllegality.setAccordingLaw(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        inspectLllegality.setLawProvisions(ExcalUtils.handleStringHSSF(row.getCell(4)));
                        inspectLllegality.setLawTerm(ExcalUtils.handleStringHSSF(row.getCell(5)));
                        inspectLllegality.setLawItem(ExcalUtils.handleStringHSSF(row.getCell(6)));
                        inspectLllegality.setContent(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        inspectLllegality.setOutdataPunishment(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        inspectLllegality.setRemark(ExcalUtils.handleStringHSSF(row.getCell(9)));
//                        inspectLllegality.setOperateIp(ExcalUtils.handleStringHSSF(row.getCell(10)));
//                        inspectLllegality.setOperator(ExcalUtils.handleStringHSSF(row.getCell(11)));
//                        inspectLllegality.setOperateTime(ExcalUtils.handleDateHSSF(row.getCell(12)));
                        inspectLllegality.setOperator("操作人");
                        inspectLllegality.setOperateIp("123.123.123");
                        inspectLllegality.setOperateTime(new Date());
                        if (inspectLllegality.getTypeId()!=null)
                        {
                            inspectLllegalityList.add(inspectLllegality);
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
                        InspectLllegality inspectLllegality = new InspectLllegality();
                        XSSFRow row = sheet.getRow(j);
//                        inspectLllegality.setTypeId();
                        //列cell
//                        inspectLllegality.setTypeId(ExcalUtils.handleIntegerHSSF(row.getCell(0)));
                        //键值对value：value，给它什么返回他的另一个值
                        inspectLllegality.setTypeId(inspectLllegalityMap.get(ExcalUtils.handleStringXSSF(row.getCell(0))));
                        inspectLllegality.setActivities(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        inspectLllegality.setRegulations(ExcalUtils.handleStringXSSF(row.getCell(2)));
                        inspectLllegality.setAccordingLaw(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        inspectLllegality.setLawProvisions(ExcalUtils.handleStringXSSF(row.getCell(4)));
                        inspectLllegality.setLawTerm(ExcalUtils.handleStringXSSF(row.getCell(5)));
                        inspectLllegality.setLawItem(ExcalUtils.handleStringXSSF(row.getCell(6)));
                        inspectLllegality.setContent(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        inspectLllegality.setOutdataPunishment(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        inspectLllegality.setRemark(ExcalUtils.handleStringXSSF(row.getCell(9)));
//                        inspectLllegality.setOperateIp(ExcalUtils.handleStringHSSF(row.getCell(10)));
//                        inspectLllegality.setOperator(ExcalUtils.handleStringHSSF(row.getCell(11)));
//                        inspectLllegality.setOperateTime(ExcalUtils.handleDateHSSF(row.getCell(12)));
                        inspectLllegality.setOperator("操作人");
                        inspectLllegality.setOperateIp("123.123.123");
                        inspectLllegality.setOperateTime(new Date());
                        if (inspectLllegality.getTypeId()!=null)
                        {
                            inspectLllegalityList.add(inspectLllegality);
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
        inspectLllegalityMapper.batchInsert(inspectLllegalityList);
    }

}
