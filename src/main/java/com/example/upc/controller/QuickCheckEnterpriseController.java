package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.QuickCheckEnterpriseSearchParam;
import com.example.upc.dataobject.QuickCheckEnterprise;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.QuickCheckEnterpriseService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/quickCheckEnterprise")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class QuickCheckEnterpriseController {
    @Autowired
    QuickCheckEnterpriseService quickCheckEnterpriseService;
    @Autowired
    SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam = JSON.parseObject(json,QuickCheckEnterpriseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {//分地区给政府人员查看
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            quickCheckEnterpriseSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(quickCheckEnterpriseService.getPage(pageQuery, quickCheckEnterpriseSearchParam));
        }
        else if (sysUser.getUserType()==4)
        {
            return CommonReturnType.create(quickCheckEnterpriseService.getUser(pageQuery, sysUser.getInfoId(), quickCheckEnterpriseSearchParam));
        }
        else
        {
            quickCheckEnterpriseService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageOk")
    @ResponseBody
    public CommonReturnType getPageOk(PageQuery pageQuery){
        return CommonReturnType.create(quickCheckEnterpriseService.getPageOk(pageQuery));

    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        QuickCheckEnterprise quickCheckEnterprise = JSONObject.parseObject(json,QuickCheckEnterprise.class);
        quickCheckEnterpriseService.insert(quickCheckEnterprise, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        QuickCheckEnterprise quickCheckEnterprise = JSONObject.parseObject(json,QuickCheckEnterprise.class);
        quickCheckEnterpriseService.update(quickCheckEnterprise);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        quickCheckEnterpriseService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/check")
    @ResponseBody
    public CommonReturnType check(int id){
        quickCheckEnterpriseService.check(id);
        return CommonReturnType.create(null);
    }
}
