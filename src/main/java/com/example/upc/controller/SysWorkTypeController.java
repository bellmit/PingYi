package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysWorkType;
import com.example.upc.service.SysWorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/5/10 19:47
 */
@Controller
@RequestMapping("/sys/workType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysWorkTypeController {
    @Autowired
    private SysWorkTypeService sysWorkTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(sysWorkTypeService.getPage(pageQuery));
    }
    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(){
        return CommonReturnType.create(sysWorkTypeService.getAll());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SysWorkType sysWorkType){
        sysWorkTypeService.insert(sysWorkType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SysWorkType sysWorkType){
        sysWorkTypeService.update(sysWorkType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        sysWorkTypeService.delete(id);
        return CommonReturnType.create(null);
    }
}
