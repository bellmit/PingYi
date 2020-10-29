package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.QuickCheckBaseSearchParam;
import com.example.upc.dataobject.QuickCheckBase;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.QuickCheckBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/quickCheckBase")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class QuickCheckBaseController {
    @Autowired
    QuickCheckBaseService quickCheckBaseService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        QuickCheckBaseSearchParam quickCheckBaseSearchParam = JSON.parseObject(json,QuickCheckBaseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) //政府人员查看接口
        {
            return CommonReturnType.create(quickCheckBaseService.getPage(pageQuery, sysUser.getInfoId(), quickCheckBaseSearchParam));
        }
        else if (sysUser.getUserType()==4)//机构人员查看接口
        {
            return CommonReturnType.create(quickCheckBaseService.getUser(pageQuery, sysUser.getInfoId(), quickCheckBaseSearchParam));
        }
        else if (sysUser.getUserType()==0) {//管理员查看服务接口
            return CommonReturnType.create(quickCheckBaseService.getPageAdmin(pageQuery, quickCheckBaseSearchParam));
        }
        //预留学校企业人员查看接口
        else
        {
            quickCheckBaseService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageStatus")
    @ResponseBody
    public CommonReturnType getPageStatus(PageQuery pageQuery){
        return CommonReturnType.create(quickCheckBaseService.getPageStatus(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        QuickCheckBase quickCheckBase = JSONObject.parseObject(json,QuickCheckBase.class);
        quickCheckBaseService.insert(quickCheckBase, sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        QuickCheckBase quickCheckBase = JSONObject.parseObject(json,QuickCheckBase.class);
        quickCheckBaseService.update(quickCheckBase);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        quickCheckBaseService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateRecord")
    @ResponseBody
    public CommonReturnType updateRecord(int id){
        quickCheckBaseService.updateRecord(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
            if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
                quickCheckBaseService.importExcel(file,3);
            }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
                quickCheckBaseService.importExcel(file,7);
            }
        return CommonReturnType.create(null);
    }

}
