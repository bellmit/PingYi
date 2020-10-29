package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.AclModuleParam;
import com.example.upc.dao.SysAclMapper;
import com.example.upc.dao.SysAclModuleMapper;
import com.example.upc.dataobject.SysAclModule;
import com.example.upc.service.SysAclModuleService;
import com.example.upc.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/4/12 11:10
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {
    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;
    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    @Transactional
    public void save(AclModuleParam param) {
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"同一层级下存在相同名称的权限模块");
        }
        SysAclModule aclModule = new SysAclModule();
        aclModule.setName(param.getName());
        aclModule.setParentId(param.getParentId());
        aclModule.setSeq(param.getSeq());
        aclModule.setStatus(param.getStatus());
        aclModule.setRemark(param.getRemark());
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setPicture(param.getPicture());
        aclModule.setOperator("操作人");
        aclModule.setOperateIp("112.123.123");
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
    }

    @Override
    @Transactional
    public void update(AclModuleParam param) {
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"同一层级下存在相同名称的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的权限模块不存在");
        }

        SysAclModule after = new SysAclModule();
        after.setId(param.getId());
        after.setName(param.getName());
        after.setParentId(param.getParentId());
        after.setSeq(param.getSeq());
        after.setStatus(param.getStatus());
        after.setRemark(param.getRemark());
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setPicture(param.getPicture());
        after.setOperator("操作人");
        after.setOperateIp("112.123.123");
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    @Override
    public void delete(int aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if(aclModule==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的权限模块不存在，无法删除");
        }
        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前模块下面有子模块，无法删除");
        }
        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前模块下面有子权限点，无法删除");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);

    }

    @Transactional
    void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(LevelUtil.calculateLevel(before.getLevel(),before.getId()));
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }
}
