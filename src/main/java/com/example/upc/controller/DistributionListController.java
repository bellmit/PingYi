package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.dataobject.DistributionList;
import com.example.upc.dataobject.StartSelfInspection;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.DistributionListService;
import com.example.upc.service.StartSelfInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/distribution")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class DistributionListController {
    @Autowired
    private DistributionListService distributionListService;

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(DistributionList distributionList, SysUser sysUser){
        distributionListService.insert(distributionList,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update( DistributionList distributionList, SysUser sysUser){
        distributionListService.update(distributionList,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        distributionListService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getByEnterpriseId")
    @ResponseBody
    public CommonReturnType getByEnterpriseId(InspectionSearchParam inspectionSearchParam, SysUser sysUser){
        List<DistributionList> distributionListArrayList = new ArrayList<>();
        distributionListArrayList = distributionListService.getByEnterpriseId(inspectionSearchParam,sysUser.getInfoId());
        return CommonReturnType.create(distributionListArrayList);
    }
}
