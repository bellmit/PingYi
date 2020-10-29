package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.MonthlySelfCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/monthlySelfCheck")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class MonthlySelfCheckController {
    @Autowired
    MonthlySelfCheckService monthlySelfCheckService;

    @RequestMapping("/insertMonSelfcheck")
    @ResponseBody
    CommonReturnType insertMonSelfcheck(@RequestBody MonthlySelfCheckParam monthlySelfCheckParam, SysUser sysUser) throws InvocationTargetException, IllegalAccessException {
         monthlySelfCheckService.insertMonSelfcheck(monthlySelfCheckParam,sysUser);
         return CommonReturnType.create(null);
    }

    @RequestMapping("/selectByDate")
    @ResponseBody
    CommonReturnType selectByDate(@RequestBody MonthlySelfCheckParam monthlySelfCheckParam,SysUser sysUser){
        return CommonReturnType.create(monthlySelfCheckService.selectByDate(monthlySelfCheckParam,sysUser));
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    CommonReturnType deleteById(@RequestBody MonthlySelfCheckParam monthlySelfCheckParam){
        monthlySelfCheckService.deleteById(monthlySelfCheckParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/standingBook")
    @ResponseBody
    CommonReturnType standingBook(@RequestBody MonthlySelfCheckParam monthlySelfCheckParam,SysUser sysUser) throws Exception {
        monthlySelfCheckService.standingBook(monthlySelfCheckParam);
        return CommonReturnType.create(null);
    }
}
