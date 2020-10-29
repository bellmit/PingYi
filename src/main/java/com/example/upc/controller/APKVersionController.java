package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.APKVersion;
import com.example.upc.service.APKVersionService;
import com.example.upc.util.GaoDeMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/APKVersion")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class APKVersionController {
    @Autowired
    private APKVersionService apkVersionService;

    @RequestMapping("/selectTopOne")
    @ResponseBody
    public CommonReturnType selectTopOne(){
        return CommonReturnType.create(apkVersionService.selectTopOne());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(APKVersion apkVersion){

        apkVersionService.insert(apkVersion);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/gps")
    @ResponseBody

    public CommonReturnType gps(String str , int a,String start, String end){
        String[] gps = str.split(",");
        Float gps1 = (float) (Float.parseFloat(gps[0]) - a * 0.00001141);
        Float gps2 = (float) (Float.parseFloat(gps[0]) + a * 0.00001141);
        Float gps3 = (float) (Float.parseFloat(gps[1]) - a * 0.00000899);
        Float gps4 = (float) (Float.parseFloat(gps[1]) + a * 0.00000899);
        List<Float> gps0 = new ArrayList<>();
        gps0.add(gps1);
        gps0.add(gps2);
        gps0.add(gps3);
        gps0.add(gps4);
        GaoDeMapUtil gaoDe = new GaoDeMapUtil();
        String b = gaoDe.getDistance(start,end);
        return CommonReturnType.create(b);
    }

}
