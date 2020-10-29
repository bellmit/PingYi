package com.example.upc.service;

import com.example.upc.dataobject.SysAcl;
import com.example.upc.dataobject.SysUser;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 11:04
 */
public interface SysCoreService {
    List<SysAcl> getCurrentUserAclList();
    List<SysAcl> getRoleAclList(int roleId);
    List<SysAcl> getUserAclList(int userId);
}
