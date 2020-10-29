package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.FoodSupervision;
import com.example.upc.service.FoodSupervisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/foodsupervision")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FoodSupervisionController {
    @Autowired
    private FoodSupervisionService foodSupervisionService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(foodSupervisionService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FoodSupervision foodSupervision){
        foodSupervisionService.insert(foodSupervision);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int fpId) {
        foodSupervisionService.delete(fpId);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FoodSupervision foodSupervision){
        foodSupervisionService.update(foodSupervision);
        return CommonReturnType.create(null);
    }
}
