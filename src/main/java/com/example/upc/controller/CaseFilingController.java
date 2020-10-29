package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.CaseFiling;
import com.example.upc.service.CaseFilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/casefiling")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class CaseFilingController {
    @Autowired
        private CaseFilingService caseFilingService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery) {
        return CommonReturnType.create(caseFilingService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(CaseFiling caseFiling){
        caseFilingService.insert(caseFiling);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int fpId) {
        caseFilingService.delete(fpId);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(CaseFiling caseFiling){
        caseFilingService.update(caseFiling);
        return CommonReturnType.create(null);
    }
}
