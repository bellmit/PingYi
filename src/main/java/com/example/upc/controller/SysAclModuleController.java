package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.AclModuleParam;
import com.example.upc.service.SysAclModuleService;
import com.example.upc.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zcc
 * @date 2019/4/12 10:16
 */
@Controller
@RequestMapping("/sys/aclModule")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysAclModuleController {
    @Autowired
    private SysAclModuleService sysAclModuleService;
    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/save")
    @ResponseBody
    public CommonReturnType saveAclModule(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        AclModuleParam  aclModuleParam = new AclModuleParam();
        aclModuleParam.setPicture(jsonObject.getString("picture"));
        aclModuleParam.setParentId(jsonObject.getInteger("parentId"));
        aclModuleParam.setName(jsonObject.getString("name"));
        aclModuleParam.setSeq(jsonObject.getInteger("seq"));
        aclModuleParam.setStatus(jsonObject.getInteger("status"));
        aclModuleParam.setRemark(jsonObject.getString("remark"));
        sysAclModuleService.save(aclModuleParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType updateAclModule(@RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        AclModuleParam  aclModuleParam = new AclModuleParam();
        aclModuleParam.setPicture(jsonObject.getString("picture"));
        aclModuleParam.setParentId(jsonObject.getInteger("parentId"));
        aclModuleParam.setName(jsonObject.getString("name"));
        aclModuleParam.setSeq(jsonObject.getInteger("seq"));
        aclModuleParam.setStatus(jsonObject.getInteger("status"));
        aclModuleParam.setRemark(jsonObject.getString("remark"));
        aclModuleParam.setId(jsonObject.getInteger("id"));
        sysAclModuleService.update(aclModuleParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/tree")
    @ResponseBody
    public CommonReturnType tree() {
        return CommonReturnType.create(sysTreeService.aclModuleTree());
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam("id") int id) {
        sysAclModuleService.delete(id);
        return CommonReturnType.create(null);
    }
}
