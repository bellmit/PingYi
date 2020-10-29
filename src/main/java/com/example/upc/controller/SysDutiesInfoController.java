package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dao.SysDutiesInfoMapper;
import com.example.upc.dataobject.SysDutiesInfo;
import com.example.upc.service.SysDutiesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/7/9 19:50
 */
@Controller
@RequestMapping("/sys/duties")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysDutiesInfoController {
    @Autowired
    private SysDutiesInfoService sysDutiesInfoService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(sysDutiesInfoService.getPage(pageQuery));

    }

    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(){
        return CommonReturnType.create(sysDutiesInfoService.getList());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SysDutiesInfo sysDutiesInfo){
        sysDutiesInfoService.insert(sysDutiesInfo);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SysDutiesInfo sysDutiesInfo){
        sysDutiesInfoService.update(sysDutiesInfo);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        sysDutiesInfoService.delete(id);
        return CommonReturnType.create(null);
    }
}
