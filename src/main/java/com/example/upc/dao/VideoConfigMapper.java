package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.VideoConfigParam;
import com.example.upc.dataobject.VideoConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoConfigMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(VideoConfig record);
    int insertSelective(VideoConfig record);
    VideoConfig selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(VideoConfig record);
    int updateByPrimaryKey(VideoConfig record);

    int countList();
    List<VideoConfigParam> getPage(@Param("page") PageQuery page);
    int countByEnterprise(@Param("enterpriseId") int enterpriseId, @Param("id") Integer id);
}