package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.searchParam.AccompanySearchParam;
import com.example.upc.controller.searchParam.BillReportSearchParam;
import com.example.upc.controller.searchParam.OriginRecordExSearchParam;
import com.example.upc.dataobject.AccompanyRecord;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.AccompanyRecordService;
import com.example.upc.service.BillReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/Accompany")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class AccompanyRecordController {
    @Autowired
    private AccompanyRecordService accompanyRecordService;

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody AccompanyRecord accompanyRecord, SysUser sysUser){
        accompanyRecordService.insert(accompanyRecord,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody AccompanyRecord accompanyRecord, SysUser sysUser){
        accompanyRecordService.update(accompanyRecord,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestBody  AccompanyRecord accompanyRecord, SysUser sysUser){
        accompanyRecordService.delete(accompanyRecord,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getAllAccompanyRecord")
    @ResponseBody
    public CommonReturnType getAllAccompanyRecord(SysUser sysUser){
        return CommonReturnType.create(accompanyRecordService.getAccompanyRecord(sysUser));
    }
    @RequestMapping("/getAccompanyRecordByDate")
    @ResponseBody
    public CommonReturnType getAccompanyRecordByDate(String date, SysUser sysUser){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate  = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CommonReturnType.create(accompanyRecordService.getAccompanyRecordByDate(startDate,sysUser));
    }
    @RequestMapping("/standingAccompanyRecord")
    @ResponseBody
    public CommonReturnType standingAccompanyRecord(Integer id, SysUser sysUser) throws Exception {
        return CommonReturnType.create(accompanyRecordService.standingAccompanyRecord(id,sysUser));
    }
}
