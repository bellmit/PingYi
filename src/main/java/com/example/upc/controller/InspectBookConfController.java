package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectBookConf;
import com.example.upc.service.InspectBookConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/7/5 15:12
 */
@Controller
@RequestMapping("/inspect/bookConf")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectBookConfController {
    @Autowired
    private InspectBookConfService inspectBookConfService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(inspectBookConfService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(InspectBookConf inspectBookConf){
        inspectBookConfService.insert(inspectBookConf);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(InspectBookConf inspectBookConf){
        inspectBookConfService.update(inspectBookConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectBookConfService.delete(id);
        return CommonReturnType.create(null);
    }
}
