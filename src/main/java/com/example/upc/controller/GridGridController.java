package com.example.upc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.*;
import com.example.upc.service.*;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/8/13 15:31
 */
@Controller
@RequestMapping("/grid/grid")
public class GridGridController {
    @Autowired
    private GridGridService gridGridService;

    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysIndustryService sysIndustryService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private  SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SysDeptService sysDeptService;
    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll(){
        return CommonReturnType.create(gridGridService.getAll());
    }

    @RequestMapping("/getTop")
    @ResponseBody
    public CommonReturnType getTop(){
        return CommonReturnType.create(gridGridService.getTop());
    }

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(gridGridService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody JSONObject json){
        GridGrid grid=new GridGrid();
        int areaId=json.getInteger("areaId");
        int parentId=gridGridService.getParentId(areaId);
        System.out.println(parentId);
        grid.setAreaId(areaId);
        grid.setParentId(parentId);
        grid.setPolygon(json.getString("polygon"));
        grid.setBorder(json.getInteger("border"));
        grid.setCenter(json.getString("center"));
        grid.setColor(json.getString("color"));
        return CommonReturnType.create(gridGridService.insertSelective(grid));
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody JSONObject json){
        GridGrid grid=new GridGrid();
        System.out.println(json.getInteger("border"));
        grid.setAreaId(json.getInteger("areaId"));
        grid.setPolygon(json.getString("polygon"));
        grid.setCenter(json.getString("center"));
        grid.setColor(json.getString("color"));
        grid.setBorder(json.getInteger("border"));
        return CommonReturnType.create(gridGridService.updateByAreaId(grid));
    }


    @RequestMapping("/getTest")
    @ResponseBody
    public CommonReturnType getTest(SysUser sysUser){
        Map<String, Object> map = Maps.newHashMap();
        if(sysUser.getUserType()==0||sysUser.getUserType()==1){
            map.put("industryList",sysIndustryService.getAll());
            map.put("areaList",sysAreaService.areaTree());
        }
        else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            map.put("industryList",sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()));
            map.put("areaList",sysDeptAreaService.getDeptAreaTree(supervisionGa.getDepartment()));
        }
        return CommonReturnType.create(map);
    }
}
