package com.example.upc.service.impl;

import com.example.upc.dao.SysRoleUserMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.SysRoleUser;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SysRoleUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zcc
 * @date 2019/4/12 11:15
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    @Override
    @Transactional
    public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }
        updateRoleUsers(roleId, userIdList);
    }

    @Override
    @Transactional
    public void changeRoleUsers(List<Integer> roleIdList, int userId) {
      List<Integer> originRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (originRoleIdList.size() == roleIdList.size()) {
            Set<Integer> originRoleIdSet = Sets.newHashSet(originRoleIdList);
            Set<Integer> roleIdSet = Sets.newHashSet(roleIdList);
            originRoleIdSet.removeAll(roleIdSet);
            if (CollectionUtils.isEmpty(originRoleIdSet)) {
                return;
            }
        }
        updateUserRoles(roleIdList,userId);
    }

    public void updateUserRoles(List<Integer> roleIdList, int userId){
        sysRoleUserMapper.deleteByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return;
        }
        List<SysRoleUser> sysRoleUsers = Lists.newArrayList();
        for(Integer roleId : roleIdList){
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(userId);
            sysRoleUser.setOperator("操作人");
            sysRoleUser.setOperateIp("124.124.124");
            sysRoleUser.setOperateTime(new Date());
            sysRoleUsers.add(sysRoleUser);
        }
        sysRoleUserMapper.batchInsert(sysRoleUsers);
    }

    public void updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);
            roleUser.setOperator("操作人");
            roleUser.setOperateIp("124.124.124");
            roleUser.setOperateTime(new Date());
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }
}
