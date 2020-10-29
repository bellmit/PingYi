package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectLllegalityConf;
import com.example.upc.service.InspectLllegalityConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/8/31 20:07
 */
@Controller
@RequestMapping("/inspect/lllegality")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectLllegalityConfController {
    @Autowired
    private InspectLllegalityConfService inspectLllegalityConfService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(inspectLllegalityConfService.getPage(pageQuery));
    }

    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(){
        return CommonReturnType.create(inspectLllegalityConfService.getList());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(InspectLllegalityConf inspectLllegalityConf) {
        inspectLllegalityConfService.insert(inspectLllegalityConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(InspectLllegalityConf inspectLllegalityConf){
        inspectLllegalityConfService.update(inspectLllegalityConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType deleteDept(@RequestParam("id") int id){
        inspectLllegalityConfService.delete(id);
        return CommonReturnType.create(null);
    }
}
