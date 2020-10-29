package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigLicence;
import com.example.upc.service.SupervisionConfigLicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:22
 */
@Controller
@RequestMapping("/supervision/licence")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionConfigLicenceController {
    @Autowired
    private SupervisionConfigLicenceService supervisionConfigLicenceService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionConfigLicenceService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionConfigLicence supervisionConfigLicence){
        supervisionConfigLicenceService.insert(supervisionConfigLicence);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionConfigLicence supervisionConfigLicence){
        supervisionConfigLicenceService.update(supervisionConfigLicence);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionConfigLicenceService.delete(id);
        return CommonReturnType.create(null);
    }
}
