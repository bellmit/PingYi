package com.example.upc.service.impl;

import com.example.upc.dao.GridMapInfoMapper;
import com.example.upc.dao.SysDeptGridMapper;
import com.example.upc.dataobject.GridMapInfo;
import com.example.upc.dataobject.SysDeptGrid;
import com.example.upc.dataobject.SysDeptIndustry;
import com.example.upc.service.SysDeptGridService;
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
public class SysDeptGridServiceImpl implements SysDeptGridService {
    @Autowired
    private SysDeptGridMapper sysDeptGridMapper;
    @Autowired
    private GridMapInfoMapper gridMapInfoMapper;
    @Override
    public List<GridMapInfo> getListByDeptId(int deptId) {
        List<Integer> gridIdList = sysDeptGridMapper.getGridIdListByDeptId(deptId);
        if (CollectionUtils.isEmpty(gridIdList)) {
            return Lists.newArrayList();
        }
        return gridMapInfoMapper.getByIdList(gridIdList);
    }

    @Override
    public List<Integer> getIdListByDeptId(int deptId) {
        return sysDeptGridMapper.getGridIdListByDeptId(deptId);
    }

    @Override
    @Transactional
    public void changeDeptGrids(int deptId, List<Integer> gridIdList) {
        List<Integer> originGridIdList = sysDeptGridMapper.getGridIdListByDeptId(deptId);
        if (originGridIdList.size() == gridIdList.size()) {
            Set<Integer> originGridIdSet = Sets.newHashSet(originGridIdList);
            Set<Integer> gridIdSet = Sets.newHashSet(gridIdList);
            originGridIdSet.removeAll(gridIdSet);
            if (CollectionUtils.isEmpty(originGridIdSet)) {
                return;
            }
        }
        updateDeptGrids(gridIdList,deptId);
    }
    public void updateDeptGrids(List<Integer> gridIdList, int deptId){
        sysDeptGridMapper.deleteByDeptId(deptId);
        if (CollectionUtils.isEmpty(gridIdList)) {
            return;
        }
        List<SysDeptGrid> sysDeptGrids = Lists.newArrayList();
        for(Integer gridId : gridIdList){
            SysDeptGrid sysDeptGrid = new SysDeptGrid();
            sysDeptGrid.setDeptId(deptId);
            sysDeptGrid.setGridId(gridId);
            sysDeptGrid.setOperator("操作人");
            sysDeptGrid.setOperateIp("124.124.124");
            sysDeptGrid.setOperateTime(new Date());
            sysDeptGrids.add(sysDeptGrid);
        }
        sysDeptGridMapper.batchInsert(sysDeptGrids);
    }
}
