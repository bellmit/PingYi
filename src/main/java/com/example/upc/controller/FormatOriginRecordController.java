package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.OriginRecordSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatOriginRecordService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatoriginrecord")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatOriginRecordController {
    @Autowired
    private FormatOriginRecordService formatOriginRecordService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;


    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json,SysUser sysUser){
        OriginRecordSearchParam originRecordSearchParam = JSON.parseObject(json,OriginRecordSearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            originRecordSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatOriginRecordService.getPage(pageQuery,originRecordSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatOriginRecordService.getPageEnterprise(pageQuery, sysUser.getInfoId(), originRecordSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatOriginRecordService.getPageAdmin(pageQuery, originRecordSearchParam));
        }
        else
        {
            formatOriginRecordService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageAnd")
    @ResponseBody
    public CommonReturnType getPageAnd(@RequestBody String json){
        OriginRecordSearchParam originRecordSearchParam = JSON.parseObject(json,OriginRecordSearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatOriginRecordService.getPageEnterprise(pageQuery,originRecordSearchParam.getId(), originRecordSearchParam));
    }


    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(int id){
        return CommonReturnType.create(formatOriginRecordService.getById(id));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatOriginRecordService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        formatOriginRecordService.insert(json,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        formatOriginRecordService.update(json,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            formatOriginRecordService.importExcel(file,3, sysUser);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            formatOriginRecordService.importExcel(file,7, sysUser);
        }
        return CommonReturnType.create(null);
    }
}
