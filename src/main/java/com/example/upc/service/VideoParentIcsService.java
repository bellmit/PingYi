package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoIcsParam;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.VideoParentIcs;

public interface VideoParentIcsService {
    PageResult<VideoParentIcs> getPageSup (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    PageResult<VideoParentIcs> getPageAdmin (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    void insert(String json, SysUser sysUser);
    void update(String json,SysUser sysUser);
    void delete(int fpId);
    VideoIcsParam getById(int id);
    VideoIcsParam selectByEnterpriseId(int id);
    void fail();
}
