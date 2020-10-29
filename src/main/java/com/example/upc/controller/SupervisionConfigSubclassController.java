package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigSubclass;
import com.example.upc.service.SupervisionConfigSubclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:24
 */
@Controller
@RequestMapping("/supervision/subclass")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionConfigSubclassController {
    @Autowired
    private SupervisionConfigSubclassService supervisionConfigSubclassService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionConfigSubclassService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionConfigSubclass supervisionConfigSubclass){
        supervisionConfigSubclassService.insert(supervisionConfigSubclass);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionConfigSubclass supervisionConfigSubclass){
        supervisionConfigSubclassService.update(supervisionConfigSubclass);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionConfigSubclassService.delete(id);
        return CommonReturnType.create(null);
    }
}
