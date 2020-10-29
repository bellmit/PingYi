package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.InspectSignSearchParam;
import com.example.upc.dataobject.InspectSignConf;

/**
 * @author zcc
 * @date 2019/8/30 21:13
 */
public interface InspectSignConfService {
    PageResult getPage (PageQuery pageQuery, InspectSignSearchParam inspectSignSearchParam);
    void insert(InspectSignConf inspectSignConf);
    void update(InspectSignConf inspectSignConf);
    void delete(int id);
}
