package com.example.upc.service;

import com.example.upc.dataobject.SysArea;
import com.example.upc.service.model.AreaLevelDto;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/10 0:29
 */
public interface SysDeptAreaService {
    List<SysArea> getListByDeptId(int deptId);
    List<AreaLevelDto> getDeptAreaTree(int deptId);
    List<Integer> getIdListByDeptId(int deptId);
    List<Integer> getIdListSearch(int searchId);
    List<Integer> getIdListByArea(int areaId);
    void changeDeptAreas (int deptId, List<Integer> areaIdList,List<Integer> areaHalfList);
}
