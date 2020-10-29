package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.EnterpriseRegulatorSearchParam;
import com.example.upc.controller.searchParam.SpotCheckBaseSearchParam;
import com.example.upc.dataobject.SpotCheckBase;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SpotCheckBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/spotCheckBase")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SpotCheckBaseController {
    @Autowired
    SpotCheckBaseService spotCheckBaseService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        SpotCheckBaseSearchParam spotCheckBaseSearchParam = JSON.parseObject(json,SpotCheckBaseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) //政府人员查看
        {
            return CommonReturnType.create(spotCheckBaseService.getPage(pageQuery, sysUser.getInfoId(), spotCheckBaseSearchParam));
        }
        else if (sysUser.getUserType()==5)//机构人员查看
        {
            return CommonReturnType.create(spotCheckBaseService.getUser(pageQuery, sysUser.getInfoId(), spotCheckBaseSearchParam));
        }
        else if (sysUser.getUserType()==0)//管理员查看
        {
            return CommonReturnType.create(spotCheckBaseService.getPageAdmin(pageQuery, spotCheckBaseSearchParam));
        }
        //预留学校企业人员查看接口
        else
        {
            spotCheckBaseService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        SpotCheckBase spotCheckBase = JSONObject.parseObject(json,SpotCheckBase.class);
        spotCheckBaseService.insert(spotCheckBase, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        SpotCheckBase spotCheckBase = JSONObject.parseObject(json,SpotCheckBase.class);
        spotCheckBaseService.update(spotCheckBase);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        spotCheckBaseService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateRecord")
    @ResponseBody
    public CommonReturnType updateRecord(int id){
        spotCheckBaseService.updateRecord(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getPageEnterpriseTeam")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json){
        EnterpriseRegulatorSearchParam enterpriseRegulatorSearchParam = JSON.parseObject(json,EnterpriseRegulatorSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        return CommonReturnType.create(spotCheckBaseService.getPageEnterpriseTeam(pageQuery,enterpriseRegulatorSearchParam));
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            spotCheckBaseService.importExcel(file,3);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            spotCheckBaseService.importExcel(file,7);
        }
        return CommonReturnType.create(null);
    }
}
