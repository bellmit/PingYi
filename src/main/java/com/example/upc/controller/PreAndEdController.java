package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.service.PreAndEdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/preAndEd")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class PreAndEdController {
    @Autowired
    private PreAndEdService preAndEdService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(preAndEdService.getPagePreAndEd(pageQuery));
    }

    @RequestMapping("/getPagePre")
    @ResponseBody
    public CommonReturnType getPagePre(PageQuery pageQuery,int dept){
        return CommonReturnType.create(preAndEdService.getPagePre(pageQuery,dept));
    }

    @RequestMapping("/getPageEd")
    @ResponseBody
    public CommonReturnType getPageEd(PageQuery pageQuery,int dept){
        return CommonReturnType.create(preAndEdService.getPageEd(pageQuery,dept));
    }
}
