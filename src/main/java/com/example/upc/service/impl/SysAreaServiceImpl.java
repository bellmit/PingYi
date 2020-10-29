package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysAreaMapper;
import com.example.upc.dataobject.SysArea;
import com.example.upc.service.SysAreaService;
import com.example.upc.service.model.AreaLevelDto;
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
 * @date 2019/7/9 20:01
 */
@Service
public class SysAreaServiceImpl implements SysAreaService {
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Override
    public PageResult<SysArea> getPage(PageQuery pageQuery) {
        int count= sysAreaMapper.countList();
        if (count > 0) {
            List<SysArea> sysAreaList = sysAreaMapper.getPage(pageQuery);
            PageResult<SysArea> pageResult = new PageResult<>();
            pageResult.setData(sysAreaList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysArea> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SysArea sysArea) {
        if (sysAreaMapper.countByNameAndParentId(sysArea.getParentId(), sysArea.getName(), sysArea.getId())>0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"同一层级下存在相同名称的区域");
        }
        sysArea.setLevel(LevelUtil.calculateLevel(getLevel(sysArea.getParentId()), sysArea.getParentId()));
        sysArea.setOperateIp("124.214.124");
        sysArea.setOperateTime(new Date());
        sysArea.setOperator("操作人");
        sysAreaMapper.insertSelective(sysArea);
    }

    @Override
    @Transactional
    public void update(SysArea sysArea) {
        if (sysAreaMapper.countByNameAndParentId(sysArea.getParentId(), sysArea.getName(), sysArea.getId())>0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"同一层级下存在相同名称的区域");
        }
        SysArea before = sysAreaMapper.selectByPrimaryKey(sysArea.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的区域不存在");
        }
        sysArea.setLevel(LevelUtil.calculateLevel(getLevel(sysArea.getParentId()), sysArea.getParentId()));
        sysArea.setOperateIp("124.214.124");
        sysArea.setOperateTime(new Date());
        sysArea.setOperator("操作人");
        updateWithChild(before, sysArea);
    }

    public void updateWithChild(SysArea before, SysArea after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysArea> sysAreaList = sysAreaMapper.getChildAreaListByLevel(LevelUtil.calculateLevel(before.getLevel(),before.getId()));
            if (CollectionUtils.isNotEmpty(sysAreaList)) {
                for (SysArea area : sysAreaList) {
                    String level = area.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        area.setLevel(level);
                    }
                }
                sysAreaMapper.batchUpdateLevel(sysAreaList);
            }
        }
        sysAreaMapper.updateByPrimaryKey(after);
    }

    private String getLevel(Integer areaId) {
        SysArea area = sysAreaMapper.selectByPrimaryKey(areaId);
        if (area == null) {
            return null;
        }
        return area.getLevel();
    }

    @Override
    public void delete(int id) {
        SysArea before = sysAreaMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的区域不存在");
        }
        if (sysAreaMapper.countByParentId(before.getId()) > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前区域下面有子区域，无法删除");
        }
        sysAreaMapper.deleteByPrimaryKey(id);
    }
    @Override
    public List<AreaLevelDto> areaTree() {
        /*
         * 获取所有地区列表
         */
        List<SysArea> areaList = sysAreaMapper.getAllArea();
        /*
        新建一个含有自己的对象的list，相当于嵌套list对象
         */
        List<AreaLevelDto> dtoList = new ArrayList<>();
        for (SysArea area : areaList) {
            /*
            adapt就是个copy方法
             */
            AreaLevelDto dto = AreaLevelDto.adapt(area);
            /*
            压入list
             */
            dtoList.add(dto);
        }
        /*
        转树结构方法，156行方法
         */
        return areaListToTree(dtoList);
    }

    public List<AreaLevelDto> areaListToTree(List<AreaLevelDto> areaLevelList) {
        if (CollectionUtils.isEmpty(areaLevelList)) {
            return new ArrayList<>();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, AreaLevelDto> levelAreaMap = ArrayListMultimap.create();//处理树
        List<AreaLevelDto> rootList = new ArrayList<>();

        for (AreaLevelDto dto : areaLevelList) {
            levelAreaMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        Collections.sort(rootList, new Comparator<AreaLevelDto>() {
            public int compare(AreaLevelDto o1, AreaLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        // 递归生成树
        transformAreatTree(rootList, LevelUtil.ROOT, levelAreaMap);
        return rootList;
    }
    public void transformAreatTree(List<AreaLevelDto> areaLevelList, String level, Multimap<String, AreaLevelDto> levelAreaMap) {
        for (int i = 0; i < areaLevelList.size(); i++) {
            // 遍历该层的每个元素
            AreaLevelDto areaLevelDto = areaLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, areaLevelDto.getId());
            // 处理下一层
            List<AreaLevelDto> tempAreaList = (List<AreaLevelDto>) levelAreaMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempAreaList)) {
                // 排序
                Collections.sort(tempAreaList, areaSeqComparator);
                // 设置下一层部门
                areaLevelDto.setChildrenList(tempAreaList);
                // 进入到下一层处理
                transformAreatTree(tempAreaList, nextLevel, levelAreaMap);
            }
        }
    }
    public Comparator<AreaLevelDto> areaSeqComparator = new Comparator<AreaLevelDto>() {
        public int compare(AreaLevelDto o1, AreaLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    @Override
    public List<SysArea> getGridByArea(int id) {
        SysArea sysArea = sysAreaMapper.selectByPrimaryKey(id);
        return sysAreaMapper.getChildAreaListByLevel(LevelUtil.calculateLevel(sysArea.getLevel(),id));
    }

    @Override
    public List<SysArea> getAllEx() {
        List<SysArea> list = sysAreaMapper.getAllAreaEx();
        return list;
    }

    @Override
    public List<SysArea> getAll() {
        return sysAreaMapper.getAllArea();
    }

    /**
     * 小程序专用serviceImpl
     */

    @Override
    public SysArea getAreaById(int id) {
        return sysAreaMapper.selectByPrimaryKey(id);
    }
}
