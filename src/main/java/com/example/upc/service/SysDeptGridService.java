package com.example.upc.service;


import com.example.upc.dataobject.GridMapInfo;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/10 0:29
 */
public interface SysDeptGridService {
    List<GridMapInfo> getListByDeptId(int deptId);
    List<Integer> getIdListByDeptId(int deptId);
    void changeDeptGrids (int deptId, List<Integer> gridIdList);
}
