package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.SpotCheckEnterpriseSearchParam;
import com.example.upc.dataobject.SpotCheckEnterprise;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.SpotCheckEnterpriseService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/spotCheckEnterprise")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckEnterpriseController {
    @Autowired
    SpotCheckEnterpriseService spotCheckEnterpriseService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam = JSON.parseObject(json,SpotCheckEnterpriseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {//分地区给政府人员查看
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            spotCheckEnterpriseSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(spotCheckEnterpriseService.getPage(pageQuery, spotCheckEnterpriseSearchParam));
        }
        else if (sysUser.getUserType()==5)
        {
            return CommonReturnType.create(spotCheckEnterpriseService.getUser(pageQuery, sysUser.getInfoId(), spotCheckEnterpriseSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(spotCheckEnterpriseService.getUserAdmin(pageQuery,spotCheckEnterpriseSearchParam));
        }
        else
        {
            spotCheckEnterpriseService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        SpotCheckEnterprise spotCheckEnterprise = JSONObject.parseObject(json,SpotCheckEnterprise.class);
        spotCheckEnterpriseService.insert(spotCheckEnterprise, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        SpotCheckEnterprise spotCheckEnterprise = JSONObject.parseObject(json,SpotCheckEnterprise.class);
        spotCheckEnterpriseService.update(spotCheckEnterprise);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        spotCheckEnterpriseService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/check")
    @ResponseBody
    public CommonReturnType check(int id){
        spotCheckEnterpriseService.check(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getPageOk")
    @ResponseBody
    public CommonReturnType getPageOk(PageQuery pageQuery){
        return CommonReturnType.create(spotCheckEnterpriseService.getPageOk(pageQuery));
    }
}
