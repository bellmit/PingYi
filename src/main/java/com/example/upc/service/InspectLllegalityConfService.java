package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.InspectLllegalityConf;

import java.util.List;

/**
 * @author zcc
 * @date 2019/8/30 21:15
 */
public interface InspectLllegalityConfService {
    PageResult getPage (PageQuery pageQuery);
    List<InspectLllegalityConf> getList();
    void insert(InspectLllegalityConf inspectLllegalityConf);
    void update(InspectLllegalityConf inspectLllegalityConf);
    void delete(int id);
}
