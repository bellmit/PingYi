package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.OriginExtraSearchParam;
import com.example.upc.dataobject.FormatOriginExtra;
import com.example.upc.dataobject.FormatOriginExtraParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatOriginExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/formatoriginextra")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatOriginExtraController {
    @Autowired
    private FormatOriginExtraService formatOriginExtraService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        OriginExtraSearchParam originExtraSearchParam = JSON.parseObject(json,OriginExtraSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatOriginExtraService.getPage(pageQuery,originExtraSearchParam, sysUser));
    }

    @RequestMapping("/getPageByListId")
    @ResponseBody
    public CommonReturnType getPageByListId(OriginExtraSearchParam originExtraSearchParam, SysUser sysUser){
        return CommonReturnType.create(formatOriginExtraService.getPageByListId(originExtraSearchParam, sysUser));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatOriginExtra formatOriginExtra, SysUser sysUser){
        formatOriginExtraService.insert(formatOriginExtra, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/insertList")
    @ResponseBody
    public CommonReturnType insertList(@RequestBody List<FormatOriginExtra> formatOriginExtraList , SysUser sysUser){
        formatOriginExtraService.insertList(formatOriginExtraList, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatOriginExtraService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatOriginExtra formatOriginExtra, SysUser sysUser){
        formatOriginExtraService.update(formatOriginExtra, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateList")
    @ResponseBody
    public CommonReturnType updateList(@RequestBody List<FormatOriginExtra> formatOriginExtraList, SysUser sysUser){
        formatOriginExtraService.updateList(formatOriginExtraList, sysUser);
        return CommonReturnType.create(null);
    }
}
