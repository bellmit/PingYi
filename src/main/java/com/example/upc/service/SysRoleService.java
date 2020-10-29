package com.example.upc.service;

import com.example.upc.controller.param.RoleParam;
import com.example.upc.dataobject.SysRole;
import com.example.upc.dataobject.SysUser;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 10:18
 */
public interface SysRoleService {
    void save(RoleParam param);
    void update(RoleParam param);
    void delete(int id);
    List<SysRole> getAll();
    List<SysRole> getList();
    List<SysRole> getRoleListByUserId(int userId);
    List<SysRole> getRoleListByAclId(int aclId);
    List<SysUser> getUserListByRoleList(List<SysRole> roleList);
}
