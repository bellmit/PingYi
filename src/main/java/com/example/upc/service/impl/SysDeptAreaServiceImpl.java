package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.dao.SysAreaMapper;
import com.example.upc.dao.SysDeptAreaMapper;
import com.example.upc.dataobject.SysArea;
import com.example.upc.dataobject.SysDeptArea;
import com.example.upc.service.SysDeptAreaService;
import com.example.upc.service.model.AreaLevelDto;
import com.example.upc.util.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zcc
 * @date 2019/7/10 10:43
 */
@Service
public class SysDeptAreaServiceImpl implements SysDeptAreaService {
    @Autowired
    private SysDeptAreaMapper sysDeptAreaMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Override
    public List<SysArea> getListByDeptId(int deptId) {
        List<Integer> areaIdList = sysDeptAreaMapper.getAreaIdListByDeptId(deptId);
        if (CollectionUtils.isEmpty(areaIdList)) {
            return Lists.newArrayList();
        }
        return sysAreaMapper.getByIdList(areaIdList);
    }

    @Override
    public List<Integer> getIdListByDeptId(int deptId) {
        return sysDeptAreaMapper.getAreaIdListByNotHalf(deptId);
    }

    @Override
    public List<Integer> getIdListSearch(int searchId) {
        SysArea sysArea = sysAreaMapper.selectByPrimaryKey(searchId);
        List<Integer> list = sysAreaMapper.getChildIdListByLevel(LevelUtil.calculateLevel(sysArea.getLevel(),searchId));
        list.add(searchId);
        return list;
    }

    @Override
    public List<Integer> getIdListByArea(int areaId) {
        List<Integer> list =sysDeptAreaMapper.getDeptIdListByAreaId(areaId);
        if(list.size()==0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"?????????????????????");
        }
        return list;
    }

    @Override
    @Transactional
    public void changeDeptAreas(int deptId, List<Integer> areaIdList,List<Integer> areaHalfList) {
        List<Integer> originAreaIdList = sysDeptAreaMapper.getAreaIdListByDeptId(deptId);
        if (originAreaIdList.size() == areaIdList.size()+areaHalfList.size()) {
            Set<Integer> originAreaIdSet = Sets.newHashSet(originAreaIdList);
            Set<Integer> areaIdSet = Sets.newHashSet(areaIdList);
            Set<Integer> areaHalfSet = Sets.newHashSet(areaHalfList);
            originAreaIdSet.removeAll(areaIdSet);
            originAreaIdSet.removeAll(areaHalfSet);
            if (CollectionUtils.isEmpty(originAreaIdSet)) {
                return;
            }
        }
        updateDeptAreas(areaIdList,deptId,areaHalfList);
    }
    public void updateDeptAreas(List<Integer> areaIdList, int deptId,List<Integer> areaHalfList){
        sysDeptAreaMapper.deleteByDeptId(deptId);
        if (CollectionUtils.isEmpty(areaIdList)) {
            return;
        }
        List<SysDeptArea> sysDeptAreas = Lists.newArrayList();
        for(Integer areaId : areaIdList){
            SysDeptArea sysDeptArea = new SysDeptArea();
            sysDeptArea.setDeptId(deptId);
            sysDeptArea.setAreaId(areaId);
            sysDeptArea.setIsHalf(0);
            sysDeptArea.setOperator("?????????");
            sysDeptArea.setOperateIp("124.124.124");
            sysDeptArea.setOperateTime(new Date());
            sysDeptAreas.add(sysDeptArea);
        }
        for (Integer areaId : areaHalfList){
            SysDeptArea sysDeptArea = new SysDeptArea();
            sysDeptArea.setDeptId(deptId);
            sysDeptArea.setAreaId(areaId);
            sysDeptArea.setIsHalf(1);
            sysDeptArea.setOperator("?????????");
            sysDeptArea.setOperateIp("124.124.124");
            sysDeptArea.setOperateTime(new Date());
            sysDeptAreas.add(sysDeptArea);
        }
        sysDeptAreaMapper.batchInsert(sysDeptAreas);
    }
    @Override
    public List<AreaLevelDto> getDeptAreaTree(int deptId) {
        List<Integer> areaIdList = sysDeptAreaMapper.getAreaIdListByDeptId(deptId);
        if (CollectionUtils.isEmpty(areaIdList)) {
            return Lists.newArrayList();
        }
        List<SysArea> areaList = sysAreaMapper.getByIdList(areaIdList);

        List<AreaLevelDto> dtoList = new ArrayList<>();
        for (SysArea area : areaList) {
            AreaLevelDto dto = AreaLevelDto.adapt(area);
            dtoList.add(dto);
        }
        return areaListToTree(dtoList);
    }

    public List<AreaLevelDto> areaListToTree(List<AreaLevelDto> areaLevelList) {
        if (CollectionUtils.isEmpty(areaLevelList)) {
            return new ArrayList<>();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, AreaLevelDto> levelAreaMap = ArrayListMultimap.create();//?????????
        List<AreaLevelDto> rootList = new ArrayList<>();

        for (AreaLevelDto dto : areaLevelList) {
            levelAreaMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // ??????seq??????????????????
        Collections.sort(rootList, new Comparator<AreaLevelDto>() {
            public int compare(AreaLevelDto o1, AreaLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // ???????????????
        transformAreatTree(rootList, LevelUtil.ROOT, levelAreaMap);
        return rootList;
    }
    public void transformAreatTree(List<AreaLevelDto> areaLevelList, String level, Multimap<String, AreaLevelDto> levelAreaMap) {
        for (int i = 0; i < areaLevelList.size(); i++) {
            // ???????????????????????????
            AreaLevelDto areaLevelDto = areaLevelList.get(i);
            // ???????????????????????????
            String nextLevel = LevelUtil.calculateLevel(level, areaLevelDto.getId());
            // ???????????????
            List<AreaLevelDto> tempAreaList = (List<AreaLevelDto>) levelAreaMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempAreaList)) {
                // ??????
                Collections.sort(tempAreaList, areaSeqComparator);
                // ?????????????????????
                areaLevelDto.setChildrenList(tempAreaList);
                // ????????????????????????
                transformAreatTree(tempAreaList, nextLevel, levelAreaMap);
            }
        }
    }
    public Comparator<AreaLevelDto> areaSeqComparator = new Comparator<AreaLevelDto>() {
        public int compare(AreaLevelDto o1, AreaLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
