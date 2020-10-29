package com.example.upc.service;

import com.example.upc.controller.searchParam.AccompanySearchParam;
import com.example.upc.dataobject.AccompanyRecord;
import com.example.upc.dataobject.FormatDisinfection;
import com.example.upc.dataobject.SysUser;

import java.util.Date;
import java.util.List;

public interface AccompanyRecordService {
    void insert(AccompanyRecord accompanyRecord, SysUser sysUser);

    void update(AccompanyRecord accompanyRecord, SysUser sysUser);

    void delete(AccompanyRecord accompanyRecord, SysUser sysUser);

    Object getAccompanyRecord(SysUser sysUser);

    List<FormatDisinfection> getAccompanyRecordByDate(Date startDate, SysUser sysUser);

    Object standingAccompanyRecord(Integer id, SysUser sysUser) throws Exception;
}
