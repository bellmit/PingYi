package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatDisinfectionParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.DisinfectionSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.FormatDisinfection;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatDisinfectionService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.stream.Collectors;

import static com.example.upc.common.EmBusinessError.USER_NO;

@Controller
@RequestMapping("/formatdisinfection")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatDisinfectionController {
    @Autowired
    private FormatDisinfectionService formatDisinfectionService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json,SysUser sysUser){
        DisinfectionSearchParam disinfectionSearchParam = JSON.parseObject(json,DisinfectionSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            disinfectionSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatDisinfectionService.getPage(pageQuery,disinfectionSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatDisinfectionService.getPageEnterprise(pageQuery, sysUser.getInfoId(), disinfectionSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatDisinfectionService.getPageAdmin(pageQuery, disinfectionSearchParam));
        }
        else
        {
            formatDisinfectionService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatDisinfectionParam formatDisinfectionParam,SysUser sysUser) throws InvocationTargetException, IllegalAccessException {
        if (sysUser.getUserType()==1){
            formatDisinfectionService.insert(formatDisinfectionParam,sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatDisinfectionService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(FormatDisinfectionParam formatDisinfectionParam) {
        formatDisinfectionService.delete(formatDisinfectionParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatDisinfectionParam formatDisinfectionParam,SysUser sysUser) throws InvocationTargetException, IllegalAccessException {
        if (sysUser.getUserType()==1){
        formatDisinfectionService.update(formatDisinfectionParam,sysUser);
        return CommonReturnType.create(null);
        }
        else {
            formatDisinfectionService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/importExcelEx")//导入excel
    @ResponseBody
    public CommonReturnType importExcelEx(MultipartFile file, SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if (sysUser.getUserType()==0) {
            if (fileName.matches("^.+\\.(?i)(xls)$")) {//03版本excel,xls
                formatDisinfectionService.importExcelEx(file, 3);
            } else if (fileName.matches("^.+\\.(?i)(xlsx)$")) {//07版本,xlsx
                formatDisinfectionService.importExcelEx(file, 7);
            }
        }
        else if (sysUser.getUserType()==1)
        {
            if (fileName.matches("^.+\\.(?i)(xls)$")) {//03版本excel,xls
                formatDisinfectionService.importExcel(file, 3,sysUser);
            } else if (fileName.matches("^.+\\.(?i)(xlsx)$")) {//07版本,xlsx
                formatDisinfectionService.importExcel(file, 7,sysUser);
            }
        }
        return CommonReturnType.create(null);
    }

    @RequestMapping("/standingBook")
    @ResponseBody
    public CommonReturnType standingBook(@RequestBody DisinfectionSearchParam disinfectionSearchParam, SysUser sysUser) throws IOException {
        disinfectionSearchParam.setEnd(new Date(disinfectionSearchParam.getEnd().getTime()+(long) 24 * 60 * 60 * 1000-1));
        return CommonReturnType.create(formatDisinfectionService.standingBook(disinfectionSearchParam,sysUser));
    }

}
