package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.InspectClauseConf;
import com.example.upc.service.InspectClauseConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/5/18 21:44
 */
@Controller
@RequestMapping("/inspect/clauseConf")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectClauseConfController {
    @Autowired
    private InspectClauseConfService inspectClauseConfService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(inspectClauseConfService.getPage(pageQuery));
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(InspectClauseConf inspectClauseConf){
        inspectClauseConfService.insert(inspectClauseConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(InspectClauseConf inspectClauseConf){
        inspectClauseConfService.update(inspectClauseConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectClauseConfService.delete(id);
        return CommonReturnType.create(null);
    }
}
