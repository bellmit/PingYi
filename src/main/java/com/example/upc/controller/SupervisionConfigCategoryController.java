package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigCategory;
import com.example.upc.service.SupervisionConfigCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:21
 */
@Controller
@RequestMapping("/supervision/category")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionConfigCategoryController {
    @Autowired
    private SupervisionConfigCategoryService supervisionConfigCategoryService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionConfigCategoryService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionConfigCategory supervisionConfigCategory){
        supervisionConfigCategoryService.insert(supervisionConfigCategory);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionConfigCategory supervisionConfigCategory){
        supervisionConfigCategoryService.update(supervisionConfigCategory);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionConfigCategoryService.delete(id);
        return CommonReturnType.create(null);
    }
}
