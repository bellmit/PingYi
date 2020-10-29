package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.FormatRecovery;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/formatrecovery")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatRecoveryController {
    @Autowired
    private FormatRecoveryService formatRecoveryService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam, SysUser sysUser){
        return CommonReturnType.create(formatRecoveryService.getPage(pageQuery, measurementSearchParam, sysUser));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatRecovery formatRecovery, SysUser sysUser){
        formatRecoveryService.insert(formatRecovery, sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatRecoveryService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatRecovery formatRecovery, SysUser sysUser){
        formatRecoveryService.update(formatRecovery, sysUser);
        return CommonReturnType.create(null);
    }
}
