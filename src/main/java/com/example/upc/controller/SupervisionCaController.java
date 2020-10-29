package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.CaParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.CaSearchParam;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysDept;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/supervision/ca")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionCaController {
    @Autowired
    private SupervisionCaService supervisionCaService;
    @Autowired
    private SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysIndustryService sysIndustryService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json,SysUser sysUser){
        JSONObject jsonObject = JSON.parseObject(json);
        CaSearchParam caSearchParam= JSON.parseObject(json,CaSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==0){
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))){
                caSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else {
                caSearchParam.setAreaList(sysDeptAreaService.getIdListSearch((Integer)jsonObject.getJSONArray("areaList").get(0)));
            }
                caSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            caSearchParam.setUserType("admin");
            return CommonReturnType.create(supervisionCaService.getPage(pageQuery,caSearchParam));
        }else if(sysUser.getUserType()==1){
            return CommonReturnType.create(supervisionCaService.getListByEnterpriseId(pageQuery,sysUser));
        }
        else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))){
                caSearchParam.setAreaList(sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment()));
            }else {
                caSearchParam.setAreaList(sysDeptAreaService.getIdListSearch((Integer)jsonObject.getJSONArray("areaList").get(0)));
            }
            caSearchParam.setIndustryList(sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    caSearchParam.setSupervisor(supervisionGa.getName());
                }
            }
            return CommonReturnType.create(supervisionCaService.getPage(pageQuery,caSearchParam));
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
    }

    @RequestMapping("/getStatistics")
    @ResponseBody
    public CommonReturnType getStatistics(SysUser sysUser){
        CaSearchParam caSearchParam = new CaSearchParam();
        EnterpriseSearchParam enterpriseSearchParam = new EnterpriseSearchParam();
        if (sysUser.getUserType()==0){
            caSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            caSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(supervisionCaService.getStatistics(caSearchParam,enterpriseSearchParam));
        }else if(sysUser.getUserType()==1){
            return CommonReturnType.create(supervisionCaService.getEnStatistics(sysUser.getInfoId()));
        }
        else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            List<SysIndustry> sysIndustryList = sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment());
            List<Integer> sysAreaList = sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment());
            caSearchParam.setIndustryList(sysIndustryList.stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            caSearchParam.setAreaList(sysAreaList);
            enterpriseSearchParam.setIndustryList(sysIndustryList.stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            enterpriseSearchParam.setAreaList(sysAreaList);
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    caSearchParam.setSupervisor(supervisionGa.getName());
                }
            }
            return CommonReturnType.create(supervisionCaService.getStatistics(caSearchParam,enterpriseSearchParam));
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        CaParam caParam = JSONObject.parseObject(json,CaParam.class);

        supervisionCaService.insert(caParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int caId) {
        supervisionCaService.delete(caId);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        CaParam caParam = JSONObject.parseObject(json,CaParam.class);
        supervisionCaService.update(caParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            supervisionCaService.importExcel(file,3);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            supervisionCaService.importExcel(file,7);
        }
        return CommonReturnType.create(null);
    }


    @RequestMapping("/getListByEnterpriseId")
    @ResponseBody
    public CommonReturnType getListByEnterpriseId(PageQuery pageQuery, SysUser sysUser) {
        return CommonReturnType.create(supervisionCaService.getListByEnterpriseId(pageQuery, sysUser));
    }

    @RequestMapping("/getSelectByEnterpriseId")
    @ResponseBody
    public CommonReturnType getSelectByEnterpriseId(SysUser sysUser) {
        return CommonReturnType.create(supervisionCaService.getAllByEnterpriseId(sysUser.getInfoId()));
    }

    @RequestMapping("/firstLoginByEnterpriseId")
    @ResponseBody
    public CommonReturnType firstLoginByEnterpriseId(int enterpriseId) {
        return CommonReturnType.create(supervisionCaService.getAllByEnterpriseId(enterpriseId));
    }

    @RequestMapping("/getByIdNumber")
    @ResponseBody
    public CommonReturnType getByIdNumber(String idNumber) throws ParseException {
        return CommonReturnType.create(supervisionCaService.getCaInfoByIdNumber(idNumber));
    }

    @RequestMapping("/getNameByEnterpriseId")
    @ResponseBody
    public CommonReturnType getNameByEnterpriseId(@RequestBody String json, SysUser sysUser) {
        PageQuery pageQuery = JSON.parseObject(json, PageQuery.class);
        MeasurementSearchParam measurementSearchParam = JSONObject.parseObject(json, MeasurementSearchParam.class);
        return CommonReturnType.create(supervisionCaService.getNameByEnterpriseId(pageQuery, sysUser, measurementSearchParam));
    }

    @RequestMapping("/getCaPageByEnterprise")
    @ResponseBody
    public CommonReturnType getCaPageByEnterprise(PageQuery pageQuery, int id) {
        return CommonReturnType.create(supervisionCaService.getCaPageByEnterprise(pageQuery, id));
    }


}

