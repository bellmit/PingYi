package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.searchParam.BillReportSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.BillReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/billReport")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class BillReportController {
    @Autowired
    private BillReportService billReportService;

    @RequestMapping("/getBillReport")
    @ResponseBody
    public CommonReturnType getBillReport(@RequestBody BillReportSearchParam billReportSearchParam, SysUser sysUser){
       return CommonReturnType.create(billReportService.getBillReport(billReportSearchParam,sysUser));
    }

    @RequestMapping("/getBillReportByBillId")
    @ResponseBody
    public CommonReturnType getBillReportByBillId(@RequestBody BillReportSearchParam billReportSearchParam, SysUser sysUser){
        return CommonReturnType.create(billReportService.getBillReportByBillId(billReportSearchParam,sysUser));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody BillReportSearchParam billReportSearchParam, SysUser sysUser){
        billReportService.insert(billReportSearchParam,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody BillReportSearchParam billReportSearchParam, SysUser sysUser){
        billReportService.update(billReportSearchParam,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestBody BillReportSearchParam billReportSearchParam, SysUser sysUser){
        billReportService.delete(billReportSearchParam,sysUser);
        return CommonReturnType.create(null);
    }
}
