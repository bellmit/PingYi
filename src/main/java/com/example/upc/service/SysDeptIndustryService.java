package com.example.upc.service;

import com.example.upc.dataobject.SysIndustry;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/10 0:30
 */
public interface SysDeptIndustryService {
    List<SysIndustry> getListByDeptId(int deptId);
    List<Integer> getIdListByDeptId(int deptId);
    void changeDeptIndustries (int deptId, List<Integer> industryIdList);
}
