package com.example.upc.service;

import com.example.upc.controller.param.BillReportParam;
import com.example.upc.controller.searchParam.BillReportSearchParam;
import com.example.upc.dataobject.BillReport;
import com.example.upc.dataobject.Billdao;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface BillReportService {
    void insert(BillReportSearchParam billReportSearchParam, SysUser sysUser);
    void update(BillReportSearchParam billReportSearchParam, SysUser sysUser);
    void delete(BillReportSearchParam billReportSearchParam, SysUser sysUser);
    List<BillReportParam> getBillReport(BillReportSearchParam billReportSearchParam, SysUser sysUser);
    List<BillReportParam> getBillReportByBillId(BillReportSearchParam billReportSearchParam, SysUser sysUser);
}
