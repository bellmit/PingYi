package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectLargeConf;
import com.example.upc.service.InspectLargeConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/5/18 21:43
 */
@Controller
@RequestMapping("/inspect/largeConf")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectLargeConfController {
    @Autowired
    private InspectLargeConfService inspectLargeConfService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(inspectLargeConfService.getPage(pageQuery));
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(InspectLargeConf inspectLargeConf){
        inspectLargeConfService.insert(inspectLargeConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(InspectLargeConf inspectLargeConf){
        inspectLargeConfService.update(inspectLargeConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectLargeConfService.delete(id);
        return CommonReturnType.create(null);
    }
}
