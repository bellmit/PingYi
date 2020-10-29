package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintProblemTypeTwo;
import com.example.upc.service.ComplaintProblemTypeTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/complaintProblemTypeTwo")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ComplaintProblemTypeTwoController {
    @Autowired
    private ComplaintProblemTypeTwoService complaintProblemTypeTwoService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintProblemTypeTwoService.getPage(pageQuery, complaintSearchParam));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(ComplaintProblemTypeTwo complaintProblemTypeTwo){
        complaintProblemTypeTwoService.insert(complaintProblemTypeTwo);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        complaintProblemTypeTwoService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(ComplaintProblemTypeTwo complaintProblemTypeTwo){
        complaintProblemTypeTwoService.update(complaintProblemTypeTwo);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getListByOne")
    @ResponseBody
    public CommonReturnType getListByOne(String oneType){
        return CommonReturnType.create(complaintProblemTypeTwoService.getListByOne(oneType));

    }

    @RequestMapping("/getPageList")
    @ResponseBody
    public CommonReturnType getPageList(ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintProblemTypeTwoService.getPageList(complaintSearchParam));
    }
}
