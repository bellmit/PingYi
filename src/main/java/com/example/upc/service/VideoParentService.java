package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoParam;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.VideoParent;

import java.util.List;

public interface VideoParentService {
    PageResult<VideoParent> getPageSup (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    PageResult<VideoParent> getPageAdmin (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    void insert(String json, SysUser sysUser);
    void update(String json,SysUser sysUser);
    void delete(int fpId);
    VideoParam getById(int id);
    VideoParam selectByEnterpriseId(int id);
    void fail();
    /**
     * 小程序专用service
     */
    List<Object> getVideoListById(int enterpriseId);
}
