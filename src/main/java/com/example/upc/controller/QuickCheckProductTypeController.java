package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.QuickCheckProductType;
import com.example.upc.dataobject.SpotCheckProductType;
import com.example.upc.service.QuickCheckProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/quickCheckProductType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class QuickCheckProductTypeController {
    @Autowired
    private QuickCheckProductTypeService quickCheckProductTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(quickCheckProductTypeService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(QuickCheckProductType quickCheckProductType){
        quickCheckProductTypeService.insert(quickCheckProductType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        quickCheckProductTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(QuickCheckProductType quickCheckProductType){
        quickCheckProductTypeService.update(quickCheckProductType);
        return CommonReturnType.create(null);
    }
}
