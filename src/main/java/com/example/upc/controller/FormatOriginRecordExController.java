package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatOriginRecordExParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.OriginRecordExSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.FormatOriginRecordEx;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatOriginRecordExService;
import com.example.upc.service.SysDeptAreaService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatOriginRecordEx")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatOriginRecordExController {
    @Autowired
    private FormatOriginRecordExService formatOriginRecordExService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        OriginRecordExSearchParam originRecordExSearchParam = JSON.parseObject(json,OriginRecordExSearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            originRecordExSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatOriginRecordExService.getPage(pageQuery,originRecordExSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatOriginRecordExService.getPageEnterprise(pageQuery, sysUser.getInfoId(), originRecordExSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatOriginRecordExService.getPageAdmin(pageQuery, originRecordExSearchParam));
        }
        else
        {
            formatOriginRecordExService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageAnd")
    @ResponseBody
    public CommonReturnType getPageAnd(@RequestBody String json){
        OriginRecordExSearchParam originRecordExSearchParam = JSON.parseObject(json,OriginRecordExSearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatOriginRecordExService.getPageEnterprise(pageQuery,originRecordExSearchParam.getId(), originRecordExSearchParam));
    }

    @RequestMapping("/getRecordExPublicByDate")
    @ResponseBody
    public CommonReturnType getRecordExPublicByDate(FormatOriginRecordEx formatOriginRecordEx, SysUser sysUser){
        return CommonReturnType.create(formatOriginRecordExService.getRecordExPublicByDate(formatOriginRecordEx,sysUser));
    }

    @RequestMapping("/getRecordExByDate")
    @ResponseBody
    public CommonReturnType getRecordExByDate(FormatOriginRecordEx formatOriginRecordEx, SysUser sysUser){
        return CommonReturnType.create(formatOriginRecordExService.getRecordExByDate(formatOriginRecordEx,sysUser));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json,SysUser sysUser){
        FormatOriginRecordExParam formatOriginRecordExParam = JSONObject.parseObject(json,FormatOriginRecordExParam.class);
        formatOriginRecordExService.insert(formatOriginRecordExParam, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/miniInsert")
    @ResponseBody
    public CommonReturnType miniInsert(@RequestBody List<FormatOriginRecordEx> formatOriginExtraList, SysUser sysUser){
        formatOriginRecordExService.miniInsert(formatOriginExtraList, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatOriginRecordExService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        FormatOriginRecordExParam formatOriginRecordExParam = JSONObject.parseObject(json,FormatOriginRecordExParam.class);
        formatOriginRecordExService.update(formatOriginRecordExParam, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file,SysUser sysUser) {
        String fileName = file.getOriginalFilename();
        if (sysUser.getUserType()==1)//企业人员导入调用
        {
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            formatOriginRecordExService.importExcel(file,3,sysUser);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            formatOriginRecordExService.importExcel(file,7,sysUser);
        }
        }
        else //管理员导入调用
            {
                if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
                    formatOriginRecordExService.importExcelEx(file,3);
                }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
                    formatOriginRecordExService.importExcelEx(file,7);
                }
            }
        return CommonReturnType.create(null);
    }
    @RequestMapping("/standingOriginRecord")
    @ResponseBody
    public CommonReturnType standingOriginRecord(@RequestBody OriginRecordExSearchParam originRecordExSearchParam, SysUser sysUser) throws IOException {
//        wasteSearchParam.setEnd1(new Date(wasteSearchParam.getEnd1().getTime()-(long) 8 * 60 * 60 * 1000));
        originRecordExSearchParam.setEnd2(new Date(originRecordExSearchParam.getEnd2().getTime()+(long) 24 * 60 * 60 * 1000-1));
        return CommonReturnType.create(formatOriginRecordExService.standingOriginRecord(originRecordExSearchParam,sysUser));
    }
}
