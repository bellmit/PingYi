package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.CollectionEnterpriseParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.service.CollectionEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/collectionEnterprise")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class CollectionEnterpriseController {
    @Autowired
    CollectionEnterpriseService collectionEnterpriseService;

    @RequestMapping("/selectByWeChatId")
    @ResponseBody
    public CommonReturnType selectByWeChatId(@RequestBody CollectionEnterpriseParam collectionEnterpriseParam){
        return CommonReturnType.create(collectionEnterpriseService.selectByWeChatId(collectionEnterpriseParam));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody CollectionEnterpriseParam collectionEnterpriseParam) throws InvocationTargetException, IllegalAccessException{
        collectionEnterpriseService.insert(collectionEnterpriseParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestBody CollectionEnterpriseParam collectionEnterpriseParam){
        collectionEnterpriseService.delete(collectionEnterpriseParam);
        return CommonReturnType.create(null);
    }
}
