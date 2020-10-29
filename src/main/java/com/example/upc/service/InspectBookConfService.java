package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.InspectBookConf;

/**
 * @author zcc
 * @date 2019/7/5 14:50
 */
public interface InspectBookConfService {
    PageResult<InspectBookConf> getPage(PageQuery pageQuery);
    void insert(InspectBookConf inspectLargeConf);
    void update(InspectBookConf inspectLargeConf);
    void delete(int id);
}
