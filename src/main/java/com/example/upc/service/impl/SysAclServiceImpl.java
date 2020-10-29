package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.AclParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysAclMapper;
import com.example.upc.dataobject.SysAcl;
import com.example.upc.service.SysAclService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 11:16
 */
@Service
public class SysAclServiceImpl implements SysAclService {
   @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public void save(AclParam param){
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = new SysAcl();
        acl.setName(param.getName());
        acl.setAclModuleId(param.getAclModuleId());
        acl.setUrl(param.getUrl());
        acl.setType(param.getType());
        acl.setStatus(param.getStatus());
        acl.setSeq(param.getSeq());
        acl.setAclLevel(param.getAclLevel());
        acl.setRemark(param.getRemark());
        acl.setCode(generateCode());
        acl.setOperator("操作人");
        acl.setOperateTime(new Date());
        acl.setOperateIp("12.124.214.");
        sysAclMapper.insertSelective(acl);
    }

    @Override
    public void update(AclParam param) {
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的权限点不存在");
        }
        SysAcl after = new SysAcl();
        after.setId(param.getId());
        after.setName(param.getName());
        after.setAclModuleId(param.getAclModuleId());
        after.setUrl(param.getUrl());
        after.setType(param.getType());
        after.setStatus(param.getStatus());
        after.setSeq(param.getSeq());
        after.setAclLevel(param.getAclLevel());
        after.setRemark(param.getRemark());
        after.setCode(generateCode());
        after.setOperator("操作人");
        after.setOperateTime(new Date());
        after.setOperateIp("12.124.214.");

        sysAclMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page);
            PageResult<SysAcl> pageResult = new PageResult<>();
            pageResult.setData(aclList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<SysAcl> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void delete(int aclId) {
        SysAcl acl = sysAclMapper.selectByPrimaryKey(aclId);
        if(acl==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的权限不存在，无法删除");
        }
        sysAclMapper.deleteByPrimaryKey(aclId);
    }

    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }
}
