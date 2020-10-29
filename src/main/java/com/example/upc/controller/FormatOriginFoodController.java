package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.TypeSearchParam;
import com.example.upc.dataobject.FormatOriginFood;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatOriginFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/formatoriginfood")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatOriginFoodController {
    @Autowired
    private FormatOriginFoodService formatOriginFoodService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(TypeSearchParam typeSearchParam, PageQuery pageQuery, SysUser sysUser){
//        TypeSearchParam typeSearchParam = JSON.parseObject(json,TypeSearchParam.class);
//        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatOriginFoodService.getPage(pageQuery,typeSearchParam, sysUser));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatOriginFood formatOriginFood, SysUser sysUse){
        formatOriginFoodService.insert(formatOriginFood, sysUse);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatOriginFoodService.delete(id);
        return CommonReturnType.create(null);
    }


    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatOriginFood formatOriginFood, SysUser sysUse){
        formatOriginFoodService.update(formatOriginFood, sysUse);
        return CommonReturnType.create(null);
    }
}
