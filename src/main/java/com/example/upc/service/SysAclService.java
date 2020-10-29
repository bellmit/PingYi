package com.example.upc.service;

import com.example.upc.common.BusinessException;
import com.example.upc.controller.param.AclParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysAcl;

/**
 * @author zcc
 * @date 2019/3/28 11:11
 */
public interface SysAclService {
    void save(AclParam param);
    void update(AclParam param);
    PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page);
    void delete(int aclId);
}
