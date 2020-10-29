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
import com.example.upc.service.FormatPictureService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatpicture")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatPictureController {
    @Autowired
    private FormatPictureService formatPictureService;
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
            return CommonReturnType.create(formatPictureService.getPage(pageQuery,pictureSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatPictureService.getPageEnterprise(pageQuery, sysUser.getInfoId(), pictureSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatPictureService.getPageAdmin(pageQuery, pictureSearchParam));
        }
        else
        {
            formatPictureService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageByEnterpriseId")
    @ResponseBody
    public CommonReturnType getPageByEnterpriseId(PageQuery pageQuery, int id){
        return CommonReturnType.create(formatPictureService.getPageByEnterpriseId(pageQuery, id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json,SysUser sysUser){
        if (sysUser.getUserType()==1){
        FormatPictureParam formatPictureParam = JSONObject.parseObject(json,FormatPictureParam.class);
        formatPictureService.insert(formatPictureParam, sysUser);
        return CommonReturnType.create(null);
        }
        else {
            formatPictureService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatPictureService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        if (sysUser.getUserType()==1) {
            FormatPictureParam formatPictureParam = JSONObject.parseObject(json, FormatPictureParam.class);
            formatPictureService.update(formatPictureParam, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatPictureService.fail();
            return CommonReturnType.create(null);
        }
    }

}
