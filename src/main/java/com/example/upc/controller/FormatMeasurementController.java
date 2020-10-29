package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.FormatMeasurement;
import com.example.upc.dataobject.FormatQualityTime;
import com.example.upc.service.FormatMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/formatmeasurement")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatMeasurementController {
    @Autowired
    private FormatMeasurementService formatMeasurementService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json){
        MeasurementSearchParam measurementSearchParam = JSON.parseObject(json,MeasurementSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(formatMeasurementService.getPage(pageQuery,measurementSearchParam));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatMeasurement formatMeasurement){
        formatMeasurementService.insert(formatMeasurement);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatMeasurementService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatMeasurement formatMeasurement){
        formatMeasurementService.update(formatMeasurement);
        return CommonReturnType.create(null);
    }
}
