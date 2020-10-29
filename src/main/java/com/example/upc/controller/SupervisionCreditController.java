package com.example.upc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCredit;
import com.example.upc.service.SupervisionCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:25
 */
@Controller
@RequestMapping("/supervision/credit")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionCreditController {
    @Autowired
    private SupervisionCreditService supervisionCreditService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionCreditService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        SupervisionCredit supervisionCredit = JSONObject.parseObject(json,SupervisionCredit.class);
        supervisionCreditService.insert(supervisionCredit);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        SupervisionCredit supervisionCredit = JSONObject.parseObject(json,SupervisionCredit.class);
        supervisionCreditService.update(supervisionCredit);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionCreditService.delete(id);
        return CommonReturnType.create(null);
    }
}
