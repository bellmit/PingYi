package com.example.upc.service;

import com.example.upc.controller.param.NewsParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SysNewsWeixinParam;
import com.example.upc.controller.searchParam.NewsSearchParam;
import com.example.upc.dataobject.SysNews;
import com.example.upc.dataobject.SysUser;

import java.util.List;

/**
 * @author zcc
 * @date 2019/8/23 11:24
 */
public interface SysNewsService {
    PageResult<NewsParam> getPage (PageQuery pageQuery, SysUser sysUser, NewsSearchParam newsSearchParam);
    PageResult<NewsParam> getPageAndroid (PageQuery pageQuery, int type, NewsSearchParam newsSearchParam);
    List<SysNewsWeixinParam> list(String ids);
    void insert(SysNews sysNews,SysUser sysUser);
    void delete(int id);
    void check(int id);
    void update(SysNews sysNews);
    NewsParam selectById(int id);
}
