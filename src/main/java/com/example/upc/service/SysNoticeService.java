package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.SysNoticeSearchParam;
import com.example.upc.dataobject.SysNotice;

import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 11:11
 */
public interface SysNoticeService {
    PageResult<SysNotice> getPage (PageQuery pageQuery);
    void insert(SysNotice sysNotice);
    void delete(int id);
    void check(int id);
    void update(SysNotice sysNotice);
    SysNotice getById(int id);
    List<SysNotice> getPage2 (SysNoticeSearchParam sysNoticeSearchParam);
}
