package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.service.SysIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/5/18 21:45
 */
@Controller
@RequestMapping("/sys/industry")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysIndustryController {
    @Autowired
    private SysIndustryService sysIndustryService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(sysIndustryService.getPage(pageQuery));
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SysIndustry sysIndustry){
        sysIndustryService.insert(sysIndustry);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SysIndustry sysIndustry){
        sysIndustryService.update(sysIndustry);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        sysIndustryService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(){
        return CommonReturnType.create(sysIndustryService.getAll());
    }
}
