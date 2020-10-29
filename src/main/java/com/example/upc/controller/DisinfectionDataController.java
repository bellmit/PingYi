package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysDisinfectionData;
import com.example.upc.service.DisinfectionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/disinfectiondata")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class DisinfectionDataController {
    @Autowired
    private DisinfectionDataService disinfectionDataService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(disinfectionDataService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SysDisinfectionData sysDisinfectionData){
        disinfectionDataService.insert(sysDisinfectionData);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int ddId) {
        disinfectionDataService.delete(ddId);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SysDisinfectionData sysDisinfectionData){
        disinfectionDataService.update(sysDisinfectionData);
        return CommonReturnType.create(null);
    }
}
