package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.controller.searchParam.StartSelfInspectionPublicSearchParam;
import com.example.upc.dataobject.InspectionList;
import com.example.upc.dataobject.InspectionPosition;
import com.example.upc.dataobject.StartSelfInspection;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.StartSelfInspectionService;
import com.example.upc.util.JsonToImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/startSelfInspection")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class StartSelfInspectionController {
    @Autowired
    private StartSelfInspectionService startSelfInspectionService;

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody InspectionList inspectionList, SysUser sysUser){
//        startSelfInspection.setInspectTime(new Date(startSelfInspection.getInspectTime().getTime()+(long) 8 * 60 * 60 * 1000));
        startSelfInspectionService.insert(inspectionList,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update( @RequestBody InspectionList inspectionList, SysUser sysUser){
        startSelfInspectionService.update(inspectionList,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        startSelfInspectionService.delete(id);
        return CommonReturnType.create(null);
    }

//    @RequestMapping("/getByDate")
//    @ResponseBody
//    public CommonReturnType getByEnterpriseId(InspectionSearchParam inspectionSearchParam, SysUser sysUser){
//        inspectionSearchParam.setEnd1(new Date(inspectionSearchParam.getStart1().getTime() + (long) 24 * 60 * 60 * 1000));
//        List<StartSelfInspection> startSelfInspections = new ArrayList<>();
//        startSelfInspections = startSelfInspectionService.getByEnterpriseId(inspectionSearchParam,sysUser.getInfoId());
//        return CommonReturnType.create(startSelfInspections);
//    }

    @RequestMapping("/getInspectionPositionByDate")
    @ResponseBody
    public CommonReturnType getInspectionPositionByDate(InspectionSearchParam inspectionSearchParam, SysUser sysUser){
        inspectionSearchParam.setEnd1(new Date(inspectionSearchParam.getStart1().getTime() + (long) 24 * 60 * 60 * 1000-1));
        List<InspectionPosition> inspectionPositionList = new ArrayList<>();
        inspectionPositionList = startSelfInspectionService.getInspectionPositionByDate(inspectionSearchParam,sysUser.getInfoId());
        return CommonReturnType.create(inspectionPositionList);
    }

    @RequestMapping("/getInspectionByPosition")
    @ResponseBody
    public CommonReturnType getInspectionByPosition(int positionId) throws ParseException {
        List<StartSelfInspection> startSelfInspectionList = new ArrayList<>();
        startSelfInspectionList = startSelfInspectionService.getInspectionByPosition(positionId);
        return CommonReturnType.create(startSelfInspectionList);
    }
    //公众端获取开工自查
    @RequestMapping("/getInspectionByPositionPublic")
    @ResponseBody
    public CommonReturnType getInspectionByPositionPublic( InspectionSearchParam inspectionSearchParam, SysUser sysUser) throws ParseException {
        inspectionSearchParam.setEnd1(new Date(inspectionSearchParam.getStart1().getTime() + (long) 24 * 60 * 60 * 1000-1));
        List<StartSelfInspectionPublicSearchParam> inspectionPositionList = new ArrayList<>();
        inspectionPositionList = startSelfInspectionService.getInspectionByPositionPublic(inspectionSearchParam,sysUser.getInfoId());
        return CommonReturnType.create(inspectionPositionList);
    }
}
