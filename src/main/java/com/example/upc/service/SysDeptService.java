package com.example.upc.service;

import com.example.upc.controller.param.DeptParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysDept;

import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 11:11
 */
public interface SysDeptService {
    PageResult<SysDept> getPage(PageQuery pageQuery);
    void changeLeaderId(int deptId,int leaderId);
    List<Integer> getIdListSearch(int searchId);
    List<SysDept> getAll();
    void save(DeptParam param);
    void update(DeptParam param);
    void delete(int deptId);
    SysDept getById(int id);
    int countList();
}
