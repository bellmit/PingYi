package com.example.upc.service;

import com.example.upc.service.model.AclModuleLevelDto;
import com.example.upc.service.model.DeptLevelDto;

import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 13:28
 */
public interface SysTreeService {
    List<DeptLevelDto> deptTree();
    List<AclModuleLevelDto> aclModuleTree();
    List<AclModuleLevelDto> userAclTree(int userId);
    List<AclModuleLevelDto> roleTree(int roleId);
}
