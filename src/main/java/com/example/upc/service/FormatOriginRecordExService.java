package com.example.upc.service;

import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.OriginRecordExSearchParam;
import com.example.upc.dataobject.FormatOriginRecordEx;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface FormatOriginRecordExService {
    PageResult<FormatOriginRecordExEnParam> getPage (PageQuery pageQuery, OriginRecordExSearchParam originRecordExSearchParam);
    PageResult<FormatOriginRecordExEnParam> getPageEnterprise (PageQuery pageQuery, Integer id, OriginRecordExSearchParam originRecordExSearchParam);
    void insert(FormatOriginRecordExParam formatOriginRecordExParam, SysUser sysUser);
    PageResult<FormatOriginRecordExEnParam> getPageAdmin (PageQuery pageQuery, OriginRecordExSearchParam originRecordExSearchParam);
    void update(FormatOriginRecordExParam formatOriginRecordExParam,SysUser sysUser);
    void delete(int fpId);
    void fail();
    void importExcelEx(MultipartFile file, Integer type);
    void importExcel(MultipartFile file, Integer type,SysUser sysUser);

    void miniInsert(List<FormatOriginRecordEx> formatOriginExtraList, SysUser sysUser);

    List<FormatOriginRecordExListParam> getRecordExPublicByDate(FormatOriginRecordEx formatOriginRecordEx, SysUser sysUser);

    List<FormatOriginRecordExEnParam> getRecordExByDate(FormatOriginRecordEx formatOriginRecordEx, SysUser sysUser);

    Object standingOriginRecord(OriginRecordExSearchParam originRecordExSearchParam, SysUser sysUser) throws IOException;
}
