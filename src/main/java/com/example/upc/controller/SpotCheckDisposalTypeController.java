package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SpotCheckDisposalType;
import com.example.upc.service.SpotCheckDisposalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spotCheckDisposalType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckDisposalTypeController {
    @Autowired
    private SpotCheckDisposalTypeService spotCheckDisposalTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(spotCheckDisposalTypeService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SpotCheckDisposalType spotCheckDisposalType){
        spotCheckDisposalTypeService.insert(spotCheckDisposalType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        spotCheckDisposalTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SpotCheckDisposalType spotCheckDisposalType){
        spotCheckDisposalTypeService.update(spotCheckDisposalType);
        return CommonReturnType.create(null);
    }
}
