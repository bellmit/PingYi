package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.EnterpriseListResult;
import com.example.upc.controller.param.GaParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintLeaderSearchParam;
import com.example.upc.controller.searchParam.GaSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.SupervisionGaService;
import com.example.upc.service.SysRoleUserService;
import com.example.upc.service.model.GaStatistics;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupervisionGaServiceImpl implements SupervisionGaService {

    @Autowired
    private SupervisionGaMapper supervisionGaMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDutiesInfoMapper sysDutiesInfoMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;


    @Override
    public PageResult getPage(PageQuery pageQuery, GaSearchParam gaSearchParam) {
        int count= supervisionGaMapper.countList(gaSearchParam);
        if (count > 0) {
            List<SupervisionGa> gaList = supervisionGaMapper.getPage(pageQuery,gaSearchParam);
            PageResult<SupervisionGa> pageResult = new PageResult<>();
            pageResult.setData(gaList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionGa> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAllList(PageQuery pageQuery, ComplaintLeaderSearchParam complaintLeaderSearchParam) {
        int count= supervisionGaMapper.countListAllList(complaintLeaderSearchParam);
        if (count > 0) {
            List<SupervisionGa> gaList = supervisionGaMapper.getPageAllList(pageQuery, complaintLeaderSearchParam);
            PageResult<SupervisionGa> pageResult = new PageResult<>();
            pageResult.setData(gaList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionGa> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(GaParam gaParam) {
        ValidationResult result = validator.validate(gaParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(supervisionGaMapper.countByTelephone(gaParam.getIdNumber(),gaParam.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该人员");
        }
        SupervisionGa ga = new SupervisionGa();
        ga.setUnitName(gaParam.getUnitName());
        ga.setDepartment(gaParam.getDepartment());
        ga.setName(gaParam.getName());
        ga.setSexy(gaParam.getSexy());
        ga.setJob(gaParam.getJob());
        ga.setType(gaParam.getType());
        ga.setIdNumber(gaParam.getIdNumber());
        ga.setEnforce(gaParam.getEnforce());
        ga.setMobilePhone(gaParam.getMobilePhone());
        ga.setOfficePhone(gaParam.getOfficePhone());
        ga.setNumber(gaParam.getNumber());
        ga.setWorkPhone(gaParam.getWorkPhone());
        ga.setCategory(gaParam.getCategory());
        ga.setPhoto(gaParam.getPhoto());
        ga.setIsStop(gaParam.getIsStop());
        ga.setMobilePhone(gaParam.getMobilePhone());
        ga.setOperator("操作人");
        ga.setOperatorIp("124.124.124");
        ga.setOperatorTime(new Date());
        supervisionGaMapper.insertSelective(ga);
        SysUser sysUser = new SysUser();
        String encryptedPassword = MD5Util.md5("123456+");
        sysUser.setUsername(ga.getName());
        sysUser.setLoginName(ga.getMobilePhone());
        sysUser.setPassword(encryptedPassword);
        sysUser.setUserType(2);
        sysUser.setInfoName(ga.getName());
        sysUser.setInfoId(ga.getId());
        sysUser.setStatus(0);
        sysUser.setOperator("操作人");
        sysUser.setOperateIp("124.124.124");
        sysUser.setOperateTime(new Date());
        sysUserMapper.insertSelective(sysUser);
        List<SysDept> allDeptList = sysDeptMapper.getAllDept();
        List<Integer> roleUserIdList = new ArrayList<>();
        for(SysDept sysDept : allDeptList){
            if(sysDept.getId()==ga.getDepartment()){
                roleUserIdList.add(sysDept.getDefaultRole());
            }
        }
        sysRoleUserService.changeRoleUsers(roleUserIdList,sysUser.getId());
    }
    @Override
    public void delete(int gaId) {
        SupervisionGa ga = supervisionGaMapper.selectByPrimaryKey(gaId);
        if(ga==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新人员不存在，无法删除");
        }
        supervisionGaMapper.deleteByPrimaryKey(gaId);
    }
    @Override
    public void update(GaParam gaParam) {
        ValidationResult result = validator.validate(gaParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(supervisionGaMapper.countByTelephone(gaParam.getIdNumber(),gaParam.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"已有该人员");
        }
        SupervisionGa before = supervisionGaMapper.selectByPrimaryKey(gaParam.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新人员不存在");
        }
        SupervisionGa ga = new SupervisionGa();
        ga.setId(gaParam.getId());
        ga.setUnitName(gaParam.getUnitName());
        ga.setDepartment(gaParam.getDepartment());
        ga.setName(gaParam.getName());
        ga.setSexy(gaParam.getSexy());
        ga.setJob(gaParam.getJob());
        ga.setType(gaParam.getType());
        ga.setIdNumber(gaParam.getIdNumber());
        ga.setEnforce(gaParam.getEnforce());
        ga.setMobilePhone(gaParam.getMobilePhone());
        ga.setOfficePhone(gaParam.getOfficePhone());
        ga.setNumber(gaParam.getNumber());
        ga.setWorkPhone(gaParam.getWorkPhone());
        ga.setCategory(gaParam.getCategory());
        ga.setPhoto(gaParam.getPhoto());
        ga.setIsStop(gaParam.getIsStop());
        ga.setMobilePhone(gaParam.getMobilePhone());
        ga.setOperator("操作人");
        ga.setOperatorIp("124.124.124");
        ga.setOperatorTime(new Date());

        // TODO: sendEmail

        supervisionGaMapper.updateByPrimaryKeySelective(ga);
        //http://localhost:8080/ga/update?id=1&department=ssss&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }

    @Override
    public void changeDept(int id, String checkDept) {
        SupervisionGa ga = supervisionGaMapper.selectByPrimaryKey(id);
        if(ga==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新人员不存在");
        }
        List<EnterpriseListResult> enterpriseListResultList=supervisionEnterpriseMapper.selectBySupervisor(ga.getName());
        for(int i=0;i<enterpriseListResultList.size();i++){
            List<String> supervisorList = new ArrayList<>(Arrays.asList(enterpriseListResultList.get(i).getSupervisor().split(",")));
            Iterator<String> it = supervisorList.iterator();
            while(it.hasNext()) {
                String x = it.next();
                if(x.indexOf(ga.getName()) != -1) {
                    it.remove();
                }
            }
            enterpriseListResultList.get(i).setSupervisor(String.join(",",supervisorList));
        }
        supervisionEnterpriseMapper.batchUpdateSupervisor(enterpriseListResultList);
        supervisionGaMapper.changeDept(id,checkDept);
    }

    @Override
    public void changeStop(int id) {
        SupervisionGa ga = supervisionGaMapper.selectByPrimaryKey(id);
        if(ga==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新人员不存在");
        }
        int isStop;
        if(ga.getIsStop()==0){
            isStop=1;
        }else{
            isStop=0;
        }
       supervisionGaMapper.changeStop(id,isStop);
    }


    @Override
    @Transactional
    public void importExcel(MultipartFile file, Integer type) {

        List<SysDept> sysDeptList = sysDeptMapper.getAllDept();
        Map<String,Integer> deptMap = new HashMap<>();
        for (SysDept sysDept : sysDeptList){deptMap.put(sysDept.getName(),sysDept.getId());}
        List<SysDutiesInfo> sysDutiesInfoList = sysDutiesInfoMapper.getList();
        Map<String,Integer> dutiesMap = new HashMap<>();
        Map<String,Integer> typeMap = new HashMap<>();
        typeMap.put("部门",1);
        typeMap.put("负责人",2);
        typeMap.put("执法人员",3);
        typeMap.put("日常责任监管人",4);
        typeMap.put("协管员",5);
        for (SysDutiesInfo sysDutiesInfo: sysDutiesInfoList){dutiesMap.put(sysDutiesInfo.getName(),sysDutiesInfo.getId());}
        List<SupervisionGa> allGaList = supervisionGaMapper.getAll();
        Map<String,Integer> allGaMap = new HashMap<>();
        for (SupervisionGa supervisionGa : allGaList){allGaMap.put(supervisionGa.getMobilePhone(),supervisionGa.getId());}
        List<SupervisionGa> supervisionGaList = new ArrayList<>();
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
                        SupervisionGa supervisionGa = new SupervisionGa();
                        HSSFRow row = sheet.getRow(j);
                        supervisionGa.setNumber(ExcalUtils.handleIntegerHSSF(row.getCell(0)));
                        supervisionGa.setUnitName(ExcalUtils.handleStringHSSF(row.getCell(1)));
                        supervisionGa.setDepartment(deptMap.get(ExcalUtils.handleStringHSSF(row.getCell(2))));
                        supervisionGa.setName(ExcalUtils.handleStringHSSF(row.getCell(3)));
                        supervisionGa.setSexy(ExcalUtils.handleStringHSSF(row.getCell(4))=="女"?1:0);
                        supervisionGa.setJob(dutiesMap.get(ExcalUtils.handleStringHSSF(row.getCell(5))));
                        supervisionGa.setType(typeMap.get(ExcalUtils.handleStringHSSF(row.getCell(6))));
                        supervisionGa.setIdNumber(ExcalUtils.handleStringHSSF(row.getCell(7)));
                        supervisionGa.setEnforce(ExcalUtils.handleStringHSSF(row.getCell(8)));
                        supervisionGa.setMobilePhone(ExcalUtils.handleStringHSSF(row.getCell(9)));
                        supervisionGa.setOfficePhone(ExcalUtils.handleStringHSSF(row.getCell(10)));
                        supervisionGa.setWorkPhone(ExcalUtils.handleStringHSSF(row.getCell(11)));
                        supervisionGa.setIsStop(ExcalUtils.handleIntegerHSSF(row.getCell(12)));
                        supervisionGa.setOperator("操作人");
                        supervisionGa.setOperatorIp("123.123.123");
                        supervisionGa.setOperatorTime(new Date());
                        if(!supervisionGa.getMobilePhone().equals("")) {
                            supervisionGaList.add(supervisionGa);
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
                        SupervisionGa supervisionGa = new SupervisionGa();
                        XSSFRow row = sheet.getRow(j);
                        supervisionGa.setNumber(ExcalUtils.handleIntegerXSSF(row.getCell(0)));
                        supervisionGa.setUnitName(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionGa.setDepartment(deptMap.get(ExcalUtils.handleStringXSSF(row.getCell(2))));
                        supervisionGa.setName(ExcalUtils.handleStringXSSF(row.getCell(3)));
                        supervisionGa.setSexy(ExcalUtils.handleStringXSSF(row.getCell(4))=="女"?1:0);
                        supervisionGa.setJob(dutiesMap.get(ExcalUtils.handleStringXSSF(row.getCell(5))));
                        supervisionGa.setType(typeMap.get(ExcalUtils.handleStringXSSF(row.getCell(6))));
                        supervisionGa.setIdNumber(ExcalUtils.handleStringXSSF(row.getCell(7)));
                        supervisionGa.setEnforce(ExcalUtils.handleStringXSSF(row.getCell(8)));
                        supervisionGa.setMobilePhone(ExcalUtils.handleStringXSSF(row.getCell(9)));
                        supervisionGa.setOfficePhone(ExcalUtils.handleStringXSSF(row.getCell(10)));
                        supervisionGa.setWorkPhone(ExcalUtils.handleStringXSSF(row.getCell(11)));
                        supervisionGa.setIsStop(ExcalUtils.handleIntegerXSSF(row.getCell(12)));
                        supervisionGa.setOperator("操作人");
                        supervisionGa.setOperatorIp("123.123.123");
                        supervisionGa.setOperatorTime(new Date());
                        if(!supervisionGa.getMobilePhone().equals("")) {
                            supervisionGaList.add(supervisionGa);
                        }
//                        int physicalNumberOfCells = row.getPhysicalNumberOfCells();
//                        for (int k = 0; k < physicalNumberOfCells; k++) {
//                            XSSFCell cell = row.getCell(k);
//                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件错误");
        }
        List<Integer> updateIds = new ArrayList<>();
        List<SysUser> sysUserList = new ArrayList<>();
        List<SysRoleUser> sysRoleUserList = new ArrayList<>();
        Map<Integer,Integer> deptIdsMap = new HashMap<>();
        for(SysDept sysDept : sysDeptList){deptIdsMap.put(sysDept.getId(),sysDept.getDefaultRole());}
        for (SupervisionGa supervisionGa : supervisionGaList){
            if(allGaMap.get(supervisionGa.getMobilePhone())!=null){
                int id = allGaMap.get(supervisionGa.getMobilePhone());
                updateIds.add(allGaMap.get(supervisionGa.getMobilePhone()));
                supervisionGa.setId(id);
            }else {
                SysUser sysUser = new SysUser();
                String encryptedPassword = MD5Util.md5("123456+");
                sysUser.setUsername(supervisionGa.getName());
                sysUser.setLoginName(supervisionGa.getMobilePhone());
                sysUser.setPassword(encryptedPassword);
                sysUser.setUserType(2);
                sysUser.setInfoName(supervisionGa.getName());
                sysUser.setInfoId(supervisionGa.getId());
                sysUser.setDeptId(supervisionGa.getDepartment());
                sysUser.setStatus(0);
                sysUser.setOperator("操作人");
                sysUser.setOperateIp("124.124.124");
                sysUser.setOperateTime(new Date());
                sysUserList.add(sysUser);
            }
        }
        if(updateIds.size()>0){
            supervisionGaMapper.batchDelete(updateIds);
        }
        if(supervisionGaList.size()>0){
            supervisionGaMapper.batchInsert(supervisionGaList);
        }
        Map<String,Integer> gaMap = new HashMap<>();
        for(SupervisionGa supervisionGa : supervisionGaList){gaMap.put(supervisionGa.getMobilePhone(),supervisionGa.getId());}
        if(sysUserList.size()>0){
            sysUserMapper.batchInsert(sysUserList.stream().map(sysUser -> {sysUser.setInfoId(gaMap.get(sysUser.getLoginName())); return sysUser;}).collect(Collectors.toList()));
        }
        for(SysUser sysUser:sysUserList)
        {
            SysRoleUser sysRoleUser =new SysRoleUser();
            sysRoleUser.setUserId(sysUser.getId());
            sysRoleUser.setRoleId(deptIdsMap.get(sysUser.getDeptId()));
            sysRoleUser.setOperateTime(new Date());
            sysRoleUser.setOperator("操作人");
            sysRoleUser.setOperateIp("123.124.124");
            sysRoleUserList.add(sysRoleUser);
        }
        if(sysRoleUserList.size()>0){
            sysRoleUserMapper.batchInsert(sysRoleUserList);
        }
    }

    @Override
    public int countList() {
        return supervisionGaMapper.countAll();
    }

    @Override
    public SupervisionGa getById(int id) {
        return supervisionGaMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<GaStatistics> getStatistics() {
        List<SysDept> deptList = sysDeptMapper.getDeptByType(2);
        List<GaStatistics> gaStatisticsList = new ArrayList<>();
        for(SysDept sysDept:deptList){
            GaStatistics gaStatistics = new GaStatistics();
            gaStatistics.setName(sysDept.getName());
            gaStatistics.setCount(supervisionGaMapper.countByDept(sysDept.getId()));
            gaStatisticsList.add(gaStatistics);
        }
        return gaStatisticsList;
    }

    @Override
    public GaStatistics getMyDeptInfo(SysDept sysDept) {
        GaStatistics gaStatistics = new GaStatistics();
        gaStatistics.setName(sysDept.getName());
        gaStatistics.setCount(supervisionGaMapper.countByDept(sysDept.getId()));
        return gaStatistics;
    }

    @Override
    public List<SupervisionGa> getGaByAreaForMap(int areaId){
        return supervisionGaMapper.getGaByAreaForMap(areaId);
    }
}
