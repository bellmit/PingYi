package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.SysNoticeSearchParam;
import com.example.upc.dataobject.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysNoticeMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysNotice record);
    int insertSelective(SysNotice record);
    SysNotice selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysNotice record);
    int updateByPrimaryKeyWithBLOBs(SysNotice record);
    int updateByPrimaryKey(SysNotice record);

    int countList();
    List<SysNotice> getPage(@Param("page") PageQuery page);
    int changeStatus(@Param("id")Integer id,@Param("status")Integer status);
    List<SysNotice> getPage2 (@Param("sysNoticeSearchParam") SysNoticeSearchParam sysNoticeSearchParam);
}