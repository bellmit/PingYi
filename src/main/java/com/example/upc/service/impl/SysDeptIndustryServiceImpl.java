package com.example.upc.service.impl;

import com.example.upc.dao.SysDeptIndustryMapper;
import com.example.upc.dao.SysIndustryMapper;
import com.example.upc.dataobject.SysDeptIndustry;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.service.SysDeptIndustryService;
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
 * @date 2019/7/10 10:42
 */
@Service
public class SysDeptIndustryServiceImpl implements SysDeptIndustryService {
    @Autowired
    private SysDeptIndustryMapper sysDeptIndustryMapper;
    @Autowired
    private SysIndustryMapper sysIndustryMapper;
    @Override
    public List<SysIndustry> getListByDeptId(int deptId) {
        List<Integer> industryIdList = sysDeptIndustryMapper.getIndustryIdListByDeptId(deptId);
        if (CollectionUtils.isEmpty(industryIdList)) {
            return Lists.newArrayList();
        }
        return sysIndustryMapper.getByIdList(industryIdList);
    }

    @Override
    public List<Integer> getIdListByDeptId(int deptId) {
        return sysDeptIndustryMapper.getIndustryIdListByDeptId(deptId);
    }

    @Override
    @Transactional
    public void changeDeptIndustries(int deptId, List<Integer> industryIdList) {
        List<Integer> originIndustryIdList = sysDeptIndustryMapper.getIndustryIdListByDeptId(deptId);
        if (originIndustryIdList.size() == industryIdList.size()) {
            Set<Integer> originIndustryIdSet = Sets.newHashSet(originIndustryIdList);
            Set<Integer> industryIdSet = Sets.newHashSet(industryIdList);
            originIndustryIdSet.removeAll(industryIdSet);
            if (CollectionUtils.isEmpty(originIndustryIdSet)) {
                return;
            }
        }
        updateDeptIndustries(industryIdList,deptId);
    }
    public void updateDeptIndustries(List<Integer> industryIdList, int deptId){
        sysDeptIndustryMapper.deleteByDeptId(deptId);
        if (CollectionUtils.isEmpty(industryIdList)) {
            return;
        }
        List<SysDeptIndustry> sysDeptIndustries = Lists.newArrayList();
        for(Integer industryId : industryIdList){
            SysDeptIndustry sysDeptIndustry = new SysDeptIndustry();
            sysDeptIndustry.setDeptId(deptId);
            sysDeptIndustry.setIndustryId(industryId);
            sysDeptIndustry.setOperator("操作人");
            sysDeptIndustry.setOperateIp("124.124.124");
            sysDeptIndustry.setOperateTime(new Date());
            sysDeptIndustries.add(sysDeptIndustry);
        }
        sysDeptIndustryMapper.batchInsert(sysDeptIndustries);
    }
}
