package com.example.upc.service;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.LeaveSearchParam;
import com.example.upc.dataobject.FormatLeaveSample;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FormatLeaveService {
    PageResult<FormatLeaveSupParam> getPage(PageQuery pageQuery, LeaveSearchParam leaveSearchParam);
    PageResult<FormatLeaveSupParam> getPageAdmin(PageQuery pageQuery, LeaveSearchParam leaveSearchParam);
    FormatLeaveMiniParam getById(int id);
    PageResult<FormatLeaveSample> getPageEnterprise (PageQuery pageQuery, Integer id, LeaveSearchParam leaveSearchParam);
    void insert(String json, SysUser sysUser);
    void update(String json,SysUser sysUser);
    void delete(int fpId);
    void fail();
    void importExcel(MultipartFile file, Integer type, SysUser sysUser);
    void importExcelAdmin(MultipartFile file, Integer type, SysUser sysUser);
    /**
     * 小程序专用service
     */
    List<Object> getFoodSamplesRecord(int enterpriseId, Date startDate);
    void miniInsert(String json, SysUser sysUser);
    void miniUpdate(String json,SysUser sysUser);
    CommonReturnType getFormatLeaveSampleByDate(SysUser sysUser, LeaveSearchParam leaveSearchParam,PageQuery pageQuery);


    String standingBook (LeaveSearchParam leaveSearchParam, SysUser sysUser) throws IOException;

//    String updateFoodSamplesRecord(Map<String,Object> updateData);
}
