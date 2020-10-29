package com.example.upc.controller;

import com.example.upc.controller.param.MonthlySelfCheckOptCategoryParam;
import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dataobject.MonthlySelfCheck;
import com.example.upc.dataobject.MonthlySelfcheckOptCategory;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.MonthlySelfcheckOptCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/monthlySelfcheckOptCategory")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")

public class MonthlySelfcheckOptCategoryController {
    @Autowired
    MonthlySelfcheckOptCategoryService monthlySelfcheckOptCategoryService;

    @RequestMapping("/selectAllOpt")
    @ResponseBody
    MonthlySelfCheckParam selectAllOpt(@RequestBody MonthlySelfCheck monthlySelfCheck, SysUser sysUser){
        return monthlySelfcheckOptCategoryService.selectAllOpt(monthlySelfCheck,sysUser);
    }
}
