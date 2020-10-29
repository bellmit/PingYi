package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigBigClass;
import com.example.upc.service.SupervisionConfigBigClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:20
 */
@Controller
@RequestMapping("/supervision/bigClass")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionConfigBigClassController {
    @Autowired
    private SupervisionConfigBigClassService supervisionConfigBigClassService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionConfigBigClassService.getPage(pageQuery));
    }

    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(){
        return CommonReturnType.create(supervisionConfigBigClassService.getList());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionConfigBigClass supervisionConfigBigClass){
        supervisionConfigBigClassService.insert(supervisionConfigBigClass);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionConfigBigClass supervisionConfigBigClass){
        supervisionConfigBigClassService.update(supervisionConfigBigClass);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionConfigBigClassService.delete(id);
        return CommonReturnType.create(null);
    }
}
