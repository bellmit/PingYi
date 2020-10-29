package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SpotCheckDateType;
import com.example.upc.service.SpotCheckDateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spotCheckDateType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckDateTypeController {
    @Autowired
    private SpotCheckDateTypeService spotCheckDateTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(spotCheckDateTypeService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SpotCheckDateType spotCheckDateType){
        spotCheckDateTypeService.insert(spotCheckDateType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        spotCheckDateTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SpotCheckDateType spotCheckDateType){
        spotCheckDateTypeService.update(spotCheckDateType);
        return CommonReturnType.create(null);
    }

}
