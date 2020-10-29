package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.service.EWMACDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ewmacData")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class EWMACDataController {
    @Autowired
    private EWMACDataService ewmacDataService;

    //总页面返回
    @RequestMapping("/getListAll")
    @ResponseBody
    public CommonReturnType getListAll(){
        return CommonReturnType.create(ewmacDataService.getListAll());
    }
}
