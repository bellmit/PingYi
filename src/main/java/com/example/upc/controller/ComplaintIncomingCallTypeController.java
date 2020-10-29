package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintIncomingCallType;
import com.example.upc.service.ComplaintIncomingCallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/complaintIncomingCallType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ComplaintIncomingCallTypeController {
    @Autowired
    private ComplaintIncomingCallTypeService complaintIncomingCallTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintIncomingCallTypeService.getPage(pageQuery, complaintSearchParam));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(ComplaintIncomingCallType complaintIncomingCallType){
        complaintIncomingCallTypeService.insert(complaintIncomingCallType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        complaintIncomingCallTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(ComplaintIncomingCallType complaintIncomingCallType){
        complaintIncomingCallTypeService.update(complaintIncomingCallType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getPageList")
    @ResponseBody
    public CommonReturnType getPageList(ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintIncomingCallTypeService.getPageList(complaintSearchParam));
    }
}
