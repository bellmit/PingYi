package com.example.upc.service.impl;

import com.example.upc.dao.SysAclMapper;
import com.example.upc.dao.SysRoleAclMapper;
import com.example.upc.dao.SysRoleUserMapper;
import com.example.upc.dataobject.SysAcl;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SysCoreService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 11:12
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {
    @Autowired
    private SysAclMapper sysAclMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public List<SysAcl> getCurrentUserAclList() {
     //   SysUser sysUser =(SysUser)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        //  int userId = sysUser.getId();
        return getUserAclList(1);
    }

    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Integer>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }
    @Override
    public List<SysAcl> getUserAclList(int userId) {
    //    if (isSuperAdmin()) {
     //       return sysAclMapper.getAll();
     //   }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }
    public boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser =(SysUser)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if (sysUser.getLoginName().contains("admin")) {
            return true;
        }
        return false;
    }
}
