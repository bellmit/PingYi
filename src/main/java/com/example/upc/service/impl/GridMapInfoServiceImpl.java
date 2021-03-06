package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.GridMapInfoMapper;
import com.example.upc.dataobject.GridMapInfo;
import com.example.upc.service.GridMapInfoService;
import com.example.upc.service.model.GridLevelDto;
import com.example.upc.util.LevelUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zcc
 * @date 2019/7/9 23:37
 */
@Service
public class GridMapInfoServiceImpl implements GridMapInfoService {
    @Autowired
    private GridMapInfoMapper gridMapInfoMapper;

    @Override
    public PageResult<GridMapInfo> getPage(PageQuery pageQuery) {
        int count= gridMapInfoMapper.countList();
        if (count > 0) {
            List<GridMapInfo> gridMapInfoList = gridMapInfoMapper.getPage(pageQuery);
            PageResult<GridMapInfo> pageResult = new PageResult<>();
            pageResult.setData(gridMapInfoList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<GridMapInfo> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<GridLevelDto> gridTree() {
        List<GridMapInfo> gridMapInfoList = gridMapInfoMapper.getAllGrid();

        List<GridLevelDto> dtoList = new ArrayList<>();
        for (GridMapInfo gridMapInfo : gridMapInfoList) {
            GridLevelDto dto = GridLevelDto.adapt(gridMapInfo);
            dtoList.add(dto);
        }
        return gridListToTree(dtoList);
    }

    public List<GridLevelDto> gridListToTree(List<GridLevelDto> gridLevelDtoList) {
        if (CollectionUtils.isEmpty(gridLevelDtoList)) {
            return new ArrayList<>();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, GridLevelDto> levelGridMap = ArrayListMultimap.create();//?????????
        List<GridLevelDto> rootList = new ArrayList<>();

        for (GridLevelDto dto : gridLevelDtoList) {
            levelGridMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // ??????seq??????????????????
        Collections.sort(rootList, new Comparator<GridLevelDto>() {
            public int compare(GridLevelDto o1, GridLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // ???????????????
        transformAreatTree(rootList, LevelUtil.ROOT, levelGridMap);
        return rootList;
    }
    public void transformAreatTree(List<GridLevelDto> gridLevelDtoList, String level, Multimap<String, GridLevelDto> levelAreaMap) {
        for (int i = 0; i < gridLevelDtoList.size(); i++) {
            // ???????????????????????????
            GridLevelDto gridLevelDto = gridLevelDtoList.get(i);
            // ???????????????????????????
            String nextLevel = LevelUtil.calculateLevel(level, gridLevelDto.getId());
            // ???????????????
            List<GridLevelDto> tempGridList = (List<GridLevelDto>) levelAreaMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempGridList)) {
                // ??????
                Collections.sort(tempGridList, areaSeqComparator);
                // ?????????????????????
                gridLevelDto.setChildrenList(tempGridList);
                // ????????????????????????
                transformAreatTree(tempGridList, nextLevel, levelAreaMap);
            }
        }
    }
    public Comparator<GridLevelDto> areaSeqComparator = new Comparator<GridLevelDto>() {
        public int compare(GridLevelDto o1, GridLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    @Override
    @Transactional
    public void insert(GridMapInfo gridMapInfo) {
        if (gridMapInfoMapper.countByNameAndParentId(gridMapInfo.getParentId(), gridMapInfo.getName(), gridMapInfo.getId())>0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????????????????????????????");
        }
        gridMapInfo.setLevel(LevelUtil.calculateLevel(getLevel(gridMapInfo.getParentId()), gridMapInfo.getParentId()));
        gridMapInfo.setOperateIp("124.214.124");
        gridMapInfo.setOperateTime(new Date());
        gridMapInfo.setOperator("?????????");
        gridMapInfoMapper.insertSelective(gridMapInfo);
    }

    @Override
    @Transactional
    public void update(GridMapInfo gridMapInfo) {
        if (gridMapInfoMapper.countByNameAndParentId(gridMapInfo.getParentId(), gridMapInfo.getName(), gridMapInfo.getId())>0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????????????????????????????");
        }
        GridMapInfo before = gridMapInfoMapper.selectByPrimaryKey(gridMapInfo.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"???????????????????????????");
        }
        gridMapInfo.setLevel(LevelUtil.calculateLevel(getLevel(gridMapInfo.getParentId()), gridMapInfo.getParentId()));
        gridMapInfo.setOperateIp("124.214.124");
        gridMapInfo.setOperateTime(new Date());
        gridMapInfo.setOperator("?????????");
        updateWithChild(before, gridMapInfo);
    }
    public void updateWithChild(GridMapInfo before, GridMapInfo after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<GridMapInfo> gridMapInfoList = gridMapInfoMapper.getChildGridListByLevel(LevelUtil.calculateLevel(before.getLevel(), before.getId()));
            if (CollectionUtils.isNotEmpty(gridMapInfoList)) {
                for (GridMapInfo grid : gridMapInfoList) {
                    String level = grid.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        grid.setLevel(level);
                    }
                }
                gridMapInfoMapper.batchUpdateLevel(gridMapInfoList);
            }
        }
        gridMapInfoMapper.updateByPrimaryKey(after);
    }

    @Override
    public void delete(int id) {
        GridMapInfo before = gridMapInfoMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"???????????????????????????");
        }
        if (gridMapInfoMapper.countByParentId(before.getId()) > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"?????????????????????????????????????????????");
        }
        gridMapInfoMapper.deleteByPrimaryKey(id);
    }
    private String getLevel(Integer gridId) {
        GridMapInfo gridMapInfo = gridMapInfoMapper.selectByPrimaryKey(gridId);
        if (gridMapInfo == null) {
            return null;
        }
        return gridMapInfo.getLevel();
    }
}
