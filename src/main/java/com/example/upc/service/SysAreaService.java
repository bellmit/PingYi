package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SysArea;
import com.example.upc.service.model.AreaLevelDto;

import java.util.List;

/**
 * @author zcc
 * @date 2019/7/9 20:01
 */
public interface SysAreaService {
    PageResult<SysArea> getPage(PageQuery pageQuery);
    List<AreaLevelDto> areaTree();
    List<SysArea> getGridByArea(int id);
    List<SysArea> getAll();
    List<SysArea> getAllEx();
    void insert(SysArea sysArea);
    void update(SysArea sysArea);
    void delete(int id);
    /**
     * 小程序专用service
     */
    SysArea getAreaById(int id);
}
