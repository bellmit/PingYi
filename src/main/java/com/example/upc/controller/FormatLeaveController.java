package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.DisinfectionSearchParam;
import com.example.upc.controller.searchParam.LeaveSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatLeaveService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatleave")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatLeaveController {
    @Autowired
    private FormatLeaveService formatLeaveService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;


    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json,SysUser sysUser){
        LeaveSearchParam leaveSearchParam = JSON.parseObject(json,LeaveSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            leaveSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatLeaveService.getPage(pageQuery, leaveSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatLeaveService.getPageEnterprise(pageQuery, sysUser.getInfoId(), leaveSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatLeaveService.getPageAdmin(pageQuery, leaveSearchParam));
        }
        else
        {
            formatLeaveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getFormatLeaveSampleByDate")
    @ResponseBody
    public CommonReturnType getFormatLeaveSampleByDate(SysUser sysUser,@RequestBody LeaveSearchParam leaveSearchParam,PageQuery pageQuery){
       return formatLeaveService.getFormatLeaveSampleByDate(sysUser,leaveSearchParam,pageQuery);
    }

    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(int id){
        return CommonReturnType.create(formatLeaveService.getById(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1)
        {
            formatLeaveService.insert(json, sysUser);
            return CommonReturnType.create(null);
        }
        else
        {
            formatLeaveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1) {
            formatLeaveService.update(json, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatLeaveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatLeaveService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if (sysUser.getUserType()==1)//企业人员导入调用
        {
            if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
                formatLeaveService.importExcel(file,3,sysUser);
            }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
                formatLeaveService.importExcel(file,7,sysUser);
            }
        }
        else //管理员导入调用
        {
            if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
                formatLeaveService.importExcelAdmin(file,3,sysUser);
            }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
                formatLeaveService.importExcelAdmin(file,7,sysUser);
            }
        }
        return CommonReturnType.create(null);
    }

    @RequestMapping("/miniInsert")
    @ResponseBody
    public CommonReturnType miniInsert(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1)
        {
            formatLeaveService.miniInsert(json, sysUser);
            return CommonReturnType.create(null);
        }
        else
        {
            formatLeaveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/miniUpdate")
    @ResponseBody
    public CommonReturnType miniUpdate(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1) {
            formatLeaveService.miniUpdate(json, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatLeaveService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/standingBook")
    @ResponseBody
    public CommonReturnType standingBook(@RequestBody LeaveSearchParam leaveSearchParam, SysUser sysUser) throws IOException {
        leaveSearchParam.setEnd(new Date(leaveSearchParam.getEnd().getTime()+(long) 24 * 60 * 60 * 1000-1));
        return CommonReturnType.create(formatLeaveService.standingBook(leaveSearchParam,sysUser));
    }
}
