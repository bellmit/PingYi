package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.FormatRecovery;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface FormatRecoveryService {

    PageResult getPage (PageQuery pageQuery, MeasurementSearchParam measurementSearchParam, SysUser sysUser);
    void insert(FormatRecovery formatRecovery, SysUser sysUser);
    void delete(int fsId);
    void update(FormatRecovery formatRecovery, SysUser sysUser);
    void fail();
    List<FormatRecovery> getPage2 ( MeasurementSearchParam measurementSearchParam, SysUser sysUser);
}
