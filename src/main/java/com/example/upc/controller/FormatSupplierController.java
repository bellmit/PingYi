package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatSupplierParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.SupplierSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatSupplierService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatsupplier")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatSupplierController {
    @Autowired
    private FormatSupplierService formatSupplierService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        SupplierSearchParam supplierSearchParam = JSON.parseObject(json,SupplierSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            supplierSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatSupplierService.getPageSup(pageQuery, supplierSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatSupplierService.getPage(pageQuery, supplierSearchParam, sysUser));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatSupplierService.getPageAdmin(pageQuery, supplierSearchParam));
        }
        else
        {
            formatSupplierService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/getByName")
    @ResponseBody
    public CommonReturnType getByName(String name){
        return CommonReturnType.create(formatSupplierService.selectByName(name));
    }

    @RequestMapping("/getByInfo")
    @ResponseBody
    public CommonReturnType getByInfo(int id, SysUser sysUser){
        return CommonReturnType.create(formatSupplierService.selectById(id,sysUser));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        FormatSupplierParam formatSupplierParam = JSONObject.parseObject(json,FormatSupplierParam.class);
        formatSupplierService.insert(formatSupplierParam, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatSupplierService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json, SysUser sysUser){
        FormatSupplierParam formatSupplierParam = JSONObject.parseObject(json,FormatSupplierParam.class);
        formatSupplierService.update(formatSupplierParam, sysUser);
        return CommonReturnType.create(null);
    }

}
