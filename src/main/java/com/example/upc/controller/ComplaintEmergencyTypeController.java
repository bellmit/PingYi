package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintEmergencyType;
import com.example.upc.service.ComplaintEmergencyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/complaintEmergencyType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ComplaintEmergencyTypeController {
    @Autowired
    private ComplaintEmergencyTypeService complaintEmergencyTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintEmergencyTypeService.getPage(pageQuery,complaintSearchParam));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(ComplaintEmergencyType complaintEmergencyType){
        complaintEmergencyTypeService.insert(complaintEmergencyType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        complaintEmergencyTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(ComplaintEmergencyType complaintEmergencyType){
        complaintEmergencyTypeService.update(complaintEmergencyType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getPageList")
    @ResponseBody
    public CommonReturnType getPageList(ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintEmergencyTypeService.getPageList(complaintSearchParam));
    }
}
