package com.example.upc.service;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 10:55
 */
public interface SysRoleAclService {
    void changeRoleAcls(Integer roleId, List<Integer> aclIdList);
}
