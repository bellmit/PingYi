package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatGoodsParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.GoodsSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatGoodsService;
import com.example.upc.service.SysDeptAreaService;
import com.example.upc.service.ViewFormatGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatgoods")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatGoodsController {
    @Autowired
    private FormatGoodsService formatGoodsService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private ViewFormatGoodsService viewFormatGoodsService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPageIn")
    @ResponseBody
    public CommonReturnType getPageIn(@RequestBody String json, SysUser sysUser){
        GoodsSearchParam goodsSearchParam = JSON.parseObject(json,GoodsSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            goodsSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatGoodsService.getPageInSup(pageQuery,goodsSearchParam));
        }
        if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatGoodsService.getPageIn(pageQuery, sysUser.getInfoId(), goodsSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatGoodsService.getPageInAdmin(pageQuery, goodsSearchParam));
        }
        else
        {
            viewFormatGoodsService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/getPageOut")
    @ResponseBody
    public CommonReturnType getPageOut(@RequestBody String json, SysUser sysUser){
        GoodsSearchParam goodsSearchParam = JSON.parseObject(json,GoodsSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            goodsSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatGoodsService.getPageOutSup(pageQuery,goodsSearchParam));
        }
        if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatGoodsService.getPageOut(pageQuery, sysUser.getInfoId(),goodsSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatGoodsService.getPageOutAdmin(pageQuery, goodsSearchParam));
        }
        else
        {
            viewFormatGoodsService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        GoodsSearchParam goodsSearchParam = JSON.parseObject(json,GoodsSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            goodsSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(viewFormatGoodsService.getPage(pageQuery, goodsSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(viewFormatGoodsService.getPageEnterprise(pageQuery, sysUser.getInfoId(), goodsSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(viewFormatGoodsService.getPageAdmin(pageQuery,  goodsSearchParam));
        }
        else
        {
            viewFormatGoodsService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/insertIn")
    @ResponseBody
    public CommonReturnType insertIn(@RequestBody String json, SysUser sysUser){
        FormatGoodsParam formatGoodsParam = JSONObject.parseObject(json,FormatGoodsParam.class);
        formatGoodsService.insertIn(formatGoodsParam, sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/insertOut")
    @ResponseBody
    public CommonReturnType insertOut(@RequestBody String json, SysUser sysUser){
        FormatGoodsParam formatGoodsParam = JSONObject.parseObject(json,FormatGoodsParam.class);
        formatGoodsService.insertOut(formatGoodsParam, sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/deleteIn")
    @ResponseBody
    public CommonReturnType deleteIn(int id) {
        formatGoodsService.deleteIn(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/deleteOut")
    @ResponseBody
    public CommonReturnType deleteOut(int id) {
        formatGoodsService.deleteOut(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/updateIn")
    @ResponseBody
    public CommonReturnType updateIn(@RequestBody String json, SysUser sysUser){
        FormatGoodsParam formatGoodsParam = JSONObject.parseObject(json,FormatGoodsParam.class);
        formatGoodsService.updateIn(formatGoodsParam, sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/updateOut")
    @ResponseBody
    public CommonReturnType updateOut(@RequestBody String json, SysUser sysUser){
        FormatGoodsParam formatGoodsParam = JSONObject.parseObject(json,FormatGoodsParam.class);
        formatGoodsService.updateOut(formatGoodsParam, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcelIn")//导入excel
    @ResponseBody
    public CommonReturnType importExcelIn(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            formatGoodsService.importExcelIn(file,3, sysUser);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            formatGoodsService.importExcelIn(file,7, sysUser);
        }
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcelInEx")//导入excel
    @ResponseBody
    public CommonReturnType importExcelInEx(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            formatGoodsService.importExcelInEx(file,3, sysUser);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            formatGoodsService.importExcelInEx(file,7, sysUser);
        }
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcelOut")//导入excel
    @ResponseBody
    public CommonReturnType importExcelOut(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            formatGoodsService.importExcelOut(file,3, sysUser);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            formatGoodsService.importExcelOut(file,7, sysUser);
        }
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcelOutEx")//导入excel
    @ResponseBody
    public CommonReturnType importExcelOutEx(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            formatGoodsService.importExcelOutEx(file,3, sysUser);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            formatGoodsService.importExcelOutEx(file,7, sysUser);
        }
        return CommonReturnType.create(null);
    }

}
