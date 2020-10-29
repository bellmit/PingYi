package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.RoleParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.*;
import com.example.upc.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/4/12 10:13
 */
@Controller
@RequestMapping("/sys/role")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysRoleAclService sysRoleAclService;

    @RequestMapping("/save")
    @ResponseBody
    public CommonReturnType saveRole(RoleParam param) {
        sysRoleService.save(param);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType updateRole(RoleParam param) {
        sysRoleService.update(param);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam("id")int id) {
        sysRoleService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/list")
    @ResponseBody
    public CommonReturnType list() {
        return CommonReturnType.create(sysRoleService.getList());
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll() {
        return CommonReturnType.create(sysRoleService.getAll());
    }

    @RequestMapping("/roleTree")
    @ResponseBody
    public CommonReturnType roleTree(@RequestParam("roleId") int roleId) {
        return CommonReturnType.create(sysTreeService.roleTree(roleId));
    }

    @RequestMapping("/changeAcls")
    @ResponseBody
    public CommonReturnType changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return CommonReturnType.create(null);
    }
}
