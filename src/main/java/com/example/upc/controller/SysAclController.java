package com.example.upc.controller;

import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.AclParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysRole;
import com.example.upc.service.SysAclService;
import com.example.upc.service.SysRoleService;
import com.example.upc.service.SysTreeService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/3/28 13:19
 */
@Controller
@RequestMapping("/sys/acl")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysAclController {
    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/save")
    @ResponseBody
    public CommonReturnType saveAclModule(AclParam param) {
        sysAclService.save(param);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType updateAclModule(AclParam param) {
        sysAclService.update(param);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/page")
    @ResponseBody
    public CommonReturnType list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return CommonReturnType.create(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }
    @RequestMapping("/acls")
    @ResponseBody
    public CommonReturnType acls(@RequestParam("aclId") int aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return CommonReturnType.create(map);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam("aclId") int aclId) {
        sysAclService.delete(aclId);
        return CommonReturnType.create(null);
    }
}
