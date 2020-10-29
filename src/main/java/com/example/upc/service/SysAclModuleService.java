package com.example.upc.service;

import com.example.upc.controller.param.AclModuleParam;

/**
 * @author zcc
 * @date 2019/4/12 10:33
 */
public interface SysAclModuleService {
    void save(AclModuleParam param);
    void update(AclModuleParam param);
    void delete(int aclModuleId);
}
