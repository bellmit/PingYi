package com.example.upc.service;

import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.OriginRecordSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.ViewFormatOriginRecord;
import org.springframework.web.multipart.MultipartFile;

public interface FormatOriginRecordService {
    PageResult<ViewFormatOriginRecordSupParam> getPage (PageQuery pageQuery, OriginRecordSearchParam originRecordSearchParam);
    PageResult<ViewFormatOriginRecord> getPageEnterprise (PageQuery pageQuery, Integer id, OriginRecordSearchParam originRecordSearchParam);
    PageResult<ViewFormatOriginRecordSupParam> getPageAdmin (PageQuery pageQuery, OriginRecordSearchParam originRecordSearchParam);
    void insert(String json, SysUser sysUser);
    void update(String json,SysUser sysUser);
    void delete(int fpId);
    FormatOriginRecordParam getById(int id);
    void fail();
    void importExcel(MultipartFile file, Integer type, SysUser sysUser);
}
