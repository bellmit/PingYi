package com.example.upc.service;

import com.example.upc.controller.param.InspectClauseConfParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.InspectClauseConf;

/**
 * @author zcc
 * @date 2019/5/18 18:42
 */
public interface InspectClauseConfService {
    PageResult<InspectClauseConfParam> getPage(PageQuery pageQuery);
    void insert(InspectClauseConf inspectClauseConf);
    void update(InspectClauseConf inspectClauseConf);
    void delete(int id);
}
