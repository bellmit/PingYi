package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SpotCheckStepType;
import com.example.upc.service.SpotCheckStepTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spotCheckStepType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckStepTypeController {
    @Autowired
    private SpotCheckStepTypeService spotCheckStepTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(spotCheckStepTypeService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SpotCheckStepType spotCheckStepType){
        spotCheckStepTypeService.insert(spotCheckStepType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        spotCheckStepTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SpotCheckStepType spotCheckStepType){
        spotCheckStepTypeService.update(spotCheckStepType);
        return CommonReturnType.create(null);
    }
}
