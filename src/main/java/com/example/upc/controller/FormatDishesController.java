package com.example.upc.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatDishesParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.DishSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatDishesService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

import static com.example.upc.common.EmBusinessError.USER_NO;

@Controller
@RequestMapping("/formatdishes")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatDishesController {
    @Autowired
    private FormatDishesService formatDishesService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        DishSearchParam dishSearchParam = JSON.parseObject(json,DishSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            dishSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatDishesService.getPage(pageQuery, dishSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatDishesService.getPageEnterprise(pageQuery, sysUser.getInfoId(), dishSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatDishesService.getPageAdmin(pageQuery, dishSearchParam));
        }
        else
        {
            formatDishesService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1) {
            FormatDishesParam formatDishesParam = JSONObject.parseObject(json, FormatDishesParam.class);
            formatDishesService.insert(formatDishesParam, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatDishesService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int fdId) {
        formatDishesService.delete(fdId);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1){
        FormatDishesParam formatDishesParam = JSONObject.parseObject(json,FormatDishesParam.class);
        formatDishesService.update(formatDishesParam, sysUser);
        return CommonReturnType.create(null);
        }
        else {
            formatDishesService.fail();
            return CommonReturnType.create(null);
        }
    }
}
