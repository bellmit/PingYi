package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.TypeSearchParam;
import com.example.upc.dataobject.FormatOrigin;
import com.example.upc.service.FormatOriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/formatorigin")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatOriginController {
    @Autowired
    private FormatOriginService formatOriginService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json){
        TypeSearchParam typeSearchParam = JSON.parseObject(json,TypeSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatOriginService.getPage(pageQuery,typeSearchParam));
    }

    @RequestMapping("/getOrigin")
    @ResponseBody
    public CommonReturnType getOrigin(@RequestBody String json){
        TypeSearchParam typeSearchParam = JSON.parseObject(json,TypeSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatOriginService.getOrigin(pageQuery,typeSearchParam));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatOrigin formatOrigin){
    formatOriginService.insert(formatOrigin);
    return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatOriginService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatOrigin formatOrigin){
        formatOriginService.update(formatOrigin);
        return CommonReturnType.create(null);
    }
}
