package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SpecialEquipmentIndustry;
import com.example.upc.service.SpecialEquipmentIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/specialEquipmentIndustry")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpecialEquipmentIndustryController {
    @Autowired
    private SpecialEquipmentIndustryService specialEquipmentIndustryService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam){
        return CommonReturnType.create(specialEquipmentIndustryService.getPage(pageQuery,measurementSearchParam));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SpecialEquipmentIndustry specialEquipmentIndustry){
        specialEquipmentIndustryService.insert(specialEquipmentIndustry);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        specialEquipmentIndustryService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SpecialEquipmentIndustry specialEquipmentIndustry){
        specialEquipmentIndustryService.update(specialEquipmentIndustry);
        return CommonReturnType.create(null);
    }
}
