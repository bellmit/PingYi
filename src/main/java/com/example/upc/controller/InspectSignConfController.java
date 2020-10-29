package com.example.upc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.InspectSignSearchParam;
import com.example.upc.dataobject.InspectSignConf;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysDept;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.InspectSignConfService;
import com.example.upc.service.SupervisionGaService;
import com.example.upc.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zcc
 * @date 2019/8/31 20:09
 */
@Controller
@RequestMapping("/inspect/sign")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectSignConfController {
    @Autowired
    private InspectSignConfService inspectSignConfService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, InspectSignSearchParam inspectSignSearchParam , SysUser sysUser){
        if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                inspectSignSearchParam.setDeptId(sysDept.getId());
            }
        }
        return CommonReturnType.create(inspectSignConfService.getPage(pageQuery,inspectSignSearchParam));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json) {
        InspectSignConf inspectSignConf = JSONObject.parseObject(json,InspectSignConf.class);
        inspectSignConfService.insert(inspectSignConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectSignConf inspectSignConf = JSONObject.parseObject(json,InspectSignConf.class);
        inspectSignConfService.update(inspectSignConf);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType deleteDept(@RequestParam("id") int id){
        inspectSignConfService.delete(id);
        return CommonReturnType.create(null);
    }
}
