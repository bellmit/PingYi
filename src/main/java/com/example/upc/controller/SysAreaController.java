package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysArea;
import com.example.upc.service.SysAreaService;
import com.example.upc.service.model.AreaLevelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/7/10 15:08
 */
@Controller
@RequestMapping("/sys/area")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysAreaController {
    @Autowired
    private SysAreaService sysAreaService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(sysAreaService.getPage(pageQuery));
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll(){
        return CommonReturnType.create(sysAreaService.getAll());
    }

    @RequestMapping("/getAllEx")
    @ResponseBody
    public CommonReturnType getAllEx(){
        return CommonReturnType.create(sysAreaService.getAllEx());
    }

    @RequestMapping("/getGridByArea")
    @ResponseBody
    public CommonReturnType getGridByArea(int id){
        return CommonReturnType.create(sysAreaService.getGridByArea(id));
    }

  @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SysArea sysArea) {
        sysAreaService.insert(sysArea);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SysArea sysArea){
        sysAreaService.update(sysArea);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType deleteDept(@RequestParam("id") int id){
        sysAreaService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/tree")
    @ResponseBody
    public CommonReturnType tree(){
        return CommonReturnType.create(sysAreaService.areaTree());
    }
}
