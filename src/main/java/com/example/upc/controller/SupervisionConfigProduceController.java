package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionConfigProduce;
import com.example.upc.service.SupervisionConfigProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:23
 */
@Controller
@RequestMapping("/supervision/produce")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionConfigProduceController {
    @Autowired
    private SupervisionConfigProduceService supervisionConfigProduceService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionConfigProduceService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionConfigProduce supervisionConfigProduce){
        supervisionConfigProduceService.insert(supervisionConfigProduce);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionConfigProduce supervisionConfigProduce){
        supervisionConfigProduceService.update(supervisionConfigProduce);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionConfigProduceService.delete(id);
        return CommonReturnType.create(null);
    }
}
