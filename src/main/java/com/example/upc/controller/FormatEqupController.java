package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatPictureParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.PictureSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatEqupService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatEqup")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatEqupController {
    @Autowired
    private FormatEqupService formatEqupService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        PictureSearchParam pictureSearchParam = JSON.parseObject(json,PictureSearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            pictureSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatEqupService.getPage(pageQuery,pictureSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatEqupService.getPageEnterprise(pageQuery, sysUser.getInfoId(), pictureSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatEqupService.getPageAdmin(pageQuery, pictureSearchParam));
        }
        else
        {
            formatEqupService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json,SysUser sysUser){
        if (sysUser.getUserType()==1){
            FormatPictureParam formatPictureParam = JSONObject.parseObject(json,FormatPictureParam.class);
            formatEqupService.insert(formatPictureParam, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatEqupService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatEqupService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        if (sysUser.getUserType()==1) {
            FormatPictureParam formatPictureParam = JSONObject.parseObject(json, FormatPictureParam.class);
            formatEqupService.update(formatPictureParam, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatEqupService.fail();
            return CommonReturnType.create(null);
        }
    }
}
