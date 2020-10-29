package com.example.upc.service;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.MorningAttendenceParam;
import com.example.upc.controller.searchParam.MorningAttendanceSearchParam;
import com.example.upc.dataobject.AccompanyRecord;
import com.example.upc.dataobject.SysUser;

import java.io.IOException;
import java.util.Date;

public interface MorningAttendanceService {
    void insert(String json, SysUser sysUser);

    void update(MorningAttendenceParam morningAttendenceParam, SysUser sysUser);

    void delete(int id, SysUser sysUser);

    CommonReturnType getMorningAttendance(SysUser sysUser);

    CommonReturnType getMorningAttendanceByDate(Date startDate, SysUser sysUser);

    Object getMorningAttendanceExcelByDate(MorningAttendanceSearchParam morningAttendanceSearchParam, SysUser sysUser) throws IOException;
}
