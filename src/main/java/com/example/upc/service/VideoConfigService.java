package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoConfigParam;
import com.example.upc.dataobject.VideoConfig;

/**
 * @author zcc
 * @date 2019/9/6 14:38
 */
public interface VideoConfigService {
    PageResult<VideoConfigParam> getPage(PageQuery pageQuery);
    void insert(VideoConfig videoConfig);
    void update(VideoConfig videoConfig);
    void delete(int id);
}
