package com.example.upc.controller;

import com.example.upc.controller.param.CommitteCheckOptParam;
import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dataobject.CommitteeCheck;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.CommitteeCheckOptCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/committeeCheckOptCategory")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class CommitteeCheckOptCategoryController {
    @Autowired
    CommitteeCheckOptCategoryService committeeCheckOptCategoryService;

    @RequestMapping("/selectAllOpt")
    @ResponseBody
    List<CommitteCheckOptParam> selectAllOpt(@RequestBody CommitteeCheck committeeCheck, SysUser sysUser){
        return committeeCheckOptCategoryService.selectAllOpt(committeeCheck,sysUser);
    }
}
