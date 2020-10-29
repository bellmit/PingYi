package com.example.upc.service.model;

import com.example.upc.dataobject.SysAclModule;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 11:29
 */
public class AclModuleLevelDto extends SysAclModule{
    private List<AclModuleLevelDto> childrenList = Lists.newArrayList();

    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }

    public List<AclModuleLevelDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<AclModuleLevelDto> childrenList) {
        this.childrenList = childrenList;
    }

    public List<AclDto> getAclList() {
        return aclList;
    }

    public void setAclList(List<AclDto> aclList) {
        this.aclList = aclList;
    }
}
