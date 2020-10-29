package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.FoodAdditiveSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FoodAdditiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author 75186
 */
@Controller
@RequestMapping("/foodAdditive")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FoodAdditiveController {
    @Autowired
    private FoodAdditiveService foodAdditiveService;

    @RequestMapping("/selectByDate")
    @ResponseBody
    public CommonReturnType selectByDate(@RequestBody FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser){
       return CommonReturnType.create(foodAdditiveService.selectByDate(foodAdditiveSearchParam,sysUser));
    }


    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody FoodAdditiveSearchParam foodAdditiveSearchParam,SysUser sysUser){
        foodAdditiveService.insert(foodAdditiveSearchParam,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody FoodAdditiveSearchParam foodAdditiveSearchParam,SysUser sysUser){
        foodAdditiveService.update(foodAdditiveSearchParam,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestBody FoodAdditiveSearchParam foodAdditiveSearchParam){
        foodAdditiveService.delete(foodAdditiveSearchParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/standingFoodAdditive")
    @ResponseBody
    public CommonReturnType standingFoodAdditive(@RequestBody FoodAdditiveSearchParam foodAdditiveSearchParam,SysUser sysUser) throws IOException {
        return CommonReturnType.create(foodAdditiveService.standingFoodAdditive(foodAdditiveSearchParam,sysUser));
    }
}
