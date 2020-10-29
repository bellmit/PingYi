package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SpecialEquipmentTypeOne;
import com.example.upc.dataobject.SpecialEquipmentTypeThree;
import com.example.upc.dataobject.SpecialEquipmentTypeTwo;
import com.example.upc.service.SpecialEquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/specialEquipmentType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpecialEquipmentTypeController {
    @Autowired
    private SpecialEquipmentTypeService specialEquipmentTypeService;
//第一套类型
    @RequestMapping("/getPageOne")
    @ResponseBody
    public CommonReturnType getPageOne(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam){
        return CommonReturnType.create(specialEquipmentTypeService.getPageOne(pageQuery,measurementSearchParam));
    }

    @RequestMapping("/insertOne")
    @ResponseBody
    public CommonReturnType insertOne(SpecialEquipmentTypeOne specialEquipmentTypeOne){
        specialEquipmentTypeService.insertOne(specialEquipmentTypeOne);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/deleteOne")
    @ResponseBody
    public CommonReturnType deleteOne(int id) {
        specialEquipmentTypeService.deleteOne(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateOne")
    @ResponseBody
    public CommonReturnType updateOne(SpecialEquipmentTypeOne specialEquipmentTypeOne){
        specialEquipmentTypeService.updateOne(specialEquipmentTypeOne);
        return CommonReturnType.create(null);
    }
//第二套类型

    @RequestMapping("/getPageTwo")
    @ResponseBody
    public CommonReturnType getPageTwo(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam){
        return CommonReturnType.create(specialEquipmentTypeService.getPageTwo(pageQuery,measurementSearchParam));
    }

    @RequestMapping("/getPageTwoByParent")
    @ResponseBody
    public CommonReturnType getPageTwoByParent(PageQuery pageQuery, String parent, MeasurementSearchParam measurementSearchParam){
        return CommonReturnType.create(specialEquipmentTypeService.getPageTwoByParent(pageQuery, parent, measurementSearchParam));
    }

    @RequestMapping("/insertTwo")
    @ResponseBody
    public CommonReturnType insertTwo(SpecialEquipmentTypeTwo specialEquipmentTypeTwo){
        specialEquipmentTypeService.insertTwo(specialEquipmentTypeTwo);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/deleteTwo")
    @ResponseBody
    public CommonReturnType deleteTwo(int id) {
        specialEquipmentTypeService.deleteTwo(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateTwo")
    @ResponseBody
    public CommonReturnType updateTwo(SpecialEquipmentTypeTwo specialEquipmentTypeTwo){
        specialEquipmentTypeService.updateTwo(specialEquipmentTypeTwo);
        return CommonReturnType.create(null);
    }
    //第三套类型
    @RequestMapping("/getPageThree")
    @ResponseBody
    public CommonReturnType getPageThree(PageQuery pageQuery, MeasurementSearchParam measurementSearchParam){
        return CommonReturnType.create(specialEquipmentTypeService.getPageThree(pageQuery,measurementSearchParam));
    }

    @RequestMapping("/getPageThreeByParent")
    @ResponseBody
    public CommonReturnType getPageThreeByParent(PageQuery pageQuery, String parent, MeasurementSearchParam measurementSearchParam){
        return CommonReturnType.create(specialEquipmentTypeService.getPageThreeByParent(pageQuery, parent, measurementSearchParam));
    }

    @RequestMapping("/insertThree")
    @ResponseBody
    public CommonReturnType insertThree(SpecialEquipmentTypeThree specialEquipmentTypeThree){
        specialEquipmentTypeService.insertThree(specialEquipmentTypeThree);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/deleteThree")
    @ResponseBody
    public CommonReturnType deleteThree(int id) {
        specialEquipmentTypeService.deleteThree(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateThree")
    @ResponseBody
    public CommonReturnType updateThree(SpecialEquipmentTypeThree specialEquipmentTypeThree){
        specialEquipmentTypeService.updateThree(specialEquipmentTypeThree);
        return CommonReturnType.create(null);
    }
}
