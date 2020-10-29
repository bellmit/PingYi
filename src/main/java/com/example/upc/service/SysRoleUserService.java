package com.example.upc.service;

import com.example.upc.dataobject.SysUser;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 10:56
 */
public interface SysRoleUserService {
    List<SysUser> getListByRoleId(int roleId);
    void changeRoleUsers(int roleId, List<Integer> userIdList);
    void changeRoleUsers(List<Integer> roleIdList,int userId);
}
