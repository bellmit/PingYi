package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.AdditiveSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.SupervisionGaService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatadditive")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatAdditiveController {
    @Autowired
    private FormatAdditiveService formatAdditiveService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;


    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        AdditiveSearchParam additiveSearchParam = JSON.parseObject(json,AdditiveSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            additiveSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatAdditiveService.getPage(pageQuery, additiveSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatAdditiveService.getPageEnterprise(pageQuery, sysUser.getInfoId(), additiveSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatAdditiveService.getPageAdmin(pageQuery, additiveSearchParam));
        }
        else
        {
            formatAdditiveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(int id){
        return CommonReturnType.create(formatAdditiveService.getById(id));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatAdditiveService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1){
        formatAdditiveService.insert(json,sysUser);
        return CommonReturnType.create(null);
        }
        else {
            formatAdditiveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        if (sysUser.getUserType()==1){
            formatAdditiveService.update(json,sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatAdditiveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/updateRecord")
    @ResponseBody
    public CommonReturnType updateRecord(int id, String publicity){
        formatAdditiveService.updateRecord( id, publicity);
        return CommonReturnType.create(null);
    }

}
