package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintProblemTypeOne;
import com.example.upc.service.ComplaintProblemTypeOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/complaintProblemTypeOne")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ComplaintProblemTypeOneController {
    @Autowired
    private ComplaintProblemTypeOneService complaintProblemTypeOneService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintProblemTypeOneService.getPage(pageQuery, complaintSearchParam));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(ComplaintProblemTypeOne complaintProblemTypeOne){
        complaintProblemTypeOneService.insert(complaintProblemTypeOne);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        complaintProblemTypeOneService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(ComplaintProblemTypeOne complaintProblemTypeOne){
        complaintProblemTypeOneService.update(complaintProblemTypeOne);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getPageList")
    @ResponseBody
    public CommonReturnType getPageList(ComplaintSearchParam complaintSearchParam){
        return CommonReturnType.create(complaintProblemTypeOneService.getPageList(complaintSearchParam));
    }
}
