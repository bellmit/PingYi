package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dao.GridMapInfoMapper;
import com.example.upc.dataobject.GridMapInfo;
import com.example.upc.service.GridMapInfoService;
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
@RequestMapping("/grid/mapInfo")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class GridMapInfoController {
    @Autowired
    private GridMapInfoService gridMapInfoService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(gridMapInfoService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(GridMapInfo gridMapInfo) {
        gridMapInfoService.insert(gridMapInfo);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(GridMapInfo gridMapInfo){
        gridMapInfoService.update(gridMapInfo);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType deleteDept(@RequestParam("id") int id){
        gridMapInfoService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/tree")
    @ResponseBody
    public CommonReturnType tree(){
        return CommonReturnType.create(gridMapInfoService.gridTree());
    }
}
