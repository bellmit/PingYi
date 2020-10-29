package com.example.upc.service;

import com.example.upc.controller.param.FormatDisinfectionParam;
import com.example.upc.controller.param.FormatDisinfectionSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.DisinfectionSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.FormatDisinfection;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

public interface FormatDisinfectionService {
    PageResult<FormatDisinfectionSupParam> getPage (PageQuery pageQuery, DisinfectionSearchParam disinfectionSearchParam);
    PageResult<FormatDisinfectionSupParam> getPageAdmin (PageQuery pageQuery, DisinfectionSearchParam disinfectionSearchParam);
    PageResult getPageEnterprise (PageQuery pageQuery, Integer id, DisinfectionSearchParam disinfectionSearchParam);

    void insert(FormatDisinfectionParam formatDisinfectionParam, SysUser sysUser) throws InvocationTargetException, IllegalAccessException;
    void delete(FormatDisinfectionParam formatDisinfectionParam);
    void update(FormatDisinfectionParam formatDisinfectionParam, SysUser sysUser) throws InvocationTargetException, IllegalAccessException;
    void fail();
    void importExcel(MultipartFile file, Integer type,SysUser sysUser);
    void importExcelEx(MultipartFile file, Integer type);
    /**
     * 小程序专用service
     */
    List<FormatDisinfection> getDisinfectionRecord(int enterpeiseId, Date startDate);

    String standingBook (DisinfectionSearchParam disinfectionSearchParam, SysUser sysUser) throws IOException;
}
