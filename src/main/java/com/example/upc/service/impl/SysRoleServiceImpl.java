package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.RoleParam;
import com.example.upc.dao.SysRoleAclMapper;
import com.example.upc.dao.SysRoleMapper;
import com.example.upc.dao.SysRoleUserMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.SysRole;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SysRoleService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/4/12 11:14
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Override
    public void save(RoleParam param) {
        if (checkExist(param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色名称已经存在");
           // throw new ParamException("角色名称已经存在");
        }
        SysRole role = new SysRole();
        role.setName(param.getName());
        role.setStatus(param.getStatus());
        role.setType(param.getType());
        role.setAclLevel(param.getAclLevel());
        role.setRemark(param.getRemark());
        role.setOperator("操作人");
        role.setOperateIp("123.124.124");
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);
    }

    @Override
    public void update(RoleParam param) {
        if (checkExist(param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"角色名称已经存在");
          //  throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的角色不存在");
            // 待更新的权限点不存在
        }
        SysRole after = new SysRole();
        after.setId(param.getId());
        after.setName(param.getName());
        after.setStatus(param.getStatus());
        after.setType(param.getType());
        after.setAclLevel(param.getAclLevel());
        after.setRemark(param.getRemark());
        after.setOperator("操作人");
        after.setOperateIp("124.124.124");
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public void delete(int id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if(sysRole==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除角色不存在，无法删除");
        }
        sysRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }


    @Override
    public List<SysRole> getList() {
        return sysRoleMapper.getList();
    }

    @Override
    public List<SysRole> getRoleListByUserId(int userId) {
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    @Override
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    @Override
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }
    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByName(name, id) > 0;
    }
}
