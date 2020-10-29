package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintInformationComeType;
import com.example.upc.service.ComplaintInformationComeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/complaintInformationComeType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ComplaintInformationComeTypeController {
    @Autowired
    private ComplaintInformationComeTypeService complaintInformationComeTypeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintInformationComeTypeService.getPage(pageQuery, complaintSearchParam));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(ComplaintInformationComeType complaintInformationComeType){
        complaintInformationComeTypeService.insert(complaintInformationComeType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        complaintInformationComeTypeService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(ComplaintInformationComeType complaintInformationComeType){
        complaintInformationComeTypeService.update(complaintInformationComeType);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/getPageList")
    @ResponseBody
    public CommonReturnType getPageList(ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintInformationComeTypeService.getPageList(complaintSearchParam));
    }
}
