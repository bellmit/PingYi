package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SpotCheckCheckType;
import com.example.upc.service.SpotCheckCheckTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spotCheckCheckType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckCheckTypeController {
    @Autowired
    private SpotCheckCheckTypeService spotCheckCheckTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(spotCheckCheckTypeService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SpotCheckCheckType spotCheckCheckType){
        spotCheckCheckTypeService.insert(spotCheckCheckType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        spotCheckCheckTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SpotCheckCheckType spotCheckCheckType){
        spotCheckCheckTypeService.update(spotCheckCheckType);
        return CommonReturnType.create(null);
    }
}
