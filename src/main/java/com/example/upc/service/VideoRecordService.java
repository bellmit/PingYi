package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoRecordEnterpriseParam;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.VideoRecord;

public interface VideoRecordService {
    PageResult<VideoRecord> getPageSup (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    PageResult<VideoRecord> getPageAdmin (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    PageResult<VideoRecordEnterpriseParam> getPageLookSup (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    PageResult<VideoRecordEnterpriseParam> getPageLookAdmin (PageQuery pageQuery, VideoSearchParam videoSearchParam);
    PageResult<VideoRecord> getPageLookByEnterpriseId (PageQuery pageQuery, int id);
    void insert(VideoRecord videoRecord, SysUser sysUser);
    void update(VideoRecord videoRecord, SysUser sysUser);
    void delete(int fpId);
    VideoRecord getRecordById(Integer id);
    void fail();
}
