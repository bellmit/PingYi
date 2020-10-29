package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.MorningAttendenceParam;
import com.example.upc.controller.searchParam.MorningAttendanceSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.AccompanyRecord;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatEqupService;
import com.example.upc.service.MorningAttendanceService;
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
@RequestMapping("/morningAttendance")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class MorningAttendanceController {
    @Autowired
    private MorningAttendanceService morningAttendanceService;

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        morningAttendanceService.insert(json,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody  MorningAttendenceParam morningAttendenceParam, SysUser sysUser){
        morningAttendanceService.update(morningAttendenceParam,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id, SysUser sysUser){
        morningAttendanceService.delete(id,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getAllMorningAttendance")
    @ResponseBody
    public CommonReturnType getAllMorningAttendance(SysUser sysUser){
        return CommonReturnType.create(morningAttendanceService.getMorningAttendance(sysUser));
    }
    @RequestMapping("/getMorningAttendanceByDate")
    @ResponseBody
    public CommonReturnType getMorningAttendanceByDate(String date, SysUser sysUser){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate  = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return CommonReturnType.create(morningAttendanceService.getMorningAttendanceByDate(startDate,sysUser));
    }
    @RequestMapping("/getMorningAttendanceExcel")
    @ResponseBody
    public CommonReturnType getMorningAttendanceExcel(@RequestBody MorningAttendanceSearchParam morningAttendanceSearchParam, SysUser sysUser) throws IOException {
        morningAttendanceSearchParam.setEndDate(new Date(morningAttendanceSearchParam.getEndDate().getTime()+(long) 24 * 60 * 60 * 1000-1));
        return CommonReturnType.create(morningAttendanceService.getMorningAttendanceExcelByDate(morningAttendanceSearchParam,sysUser));
    }
}
