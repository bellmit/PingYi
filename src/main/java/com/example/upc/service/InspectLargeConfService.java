package com.example.upc.service;

import com.example.upc.controller.param.InspectLargeConfParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.InspectLargeConf;

import java.util.List;

/**
 * @author zcc
 * @date 2019/5/18 18:27
 */
public interface InspectLargeConfService {
    PageResult<InspectLargeConfParam> getPage(PageQuery pageQuery);
    void insert(InspectLargeConf inspectLargeConf);
    void update(InspectLargeConf inspectLargeConf);
    void delete(int id);
    List<InspectLargeConf> getAll();
}
