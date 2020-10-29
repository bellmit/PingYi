package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.CommitteeCheckParam;
import com.example.upc.controller.param.MonthlySelfCheckParam;
import com.example.upc.dataobject.CommitteeCheck;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.CommitteeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * @author 董志涵
 */
@Controller
@RequestMapping("/committeeCheck")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class CommitteeCheckController {
    @Autowired
    private CommitteeCheckService committeeCheckService;

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody CommitteeCheckParam committeeCheckParam, SysUser sysUser)  {
        committeeCheckService.insert(committeeCheckParam,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getByDate")
    @ResponseBody
    public CommonReturnType getByDate(@RequestBody CommitteeCheckParam committeeCheckParam, SysUser sysUser)  {
        List<CommitteeCheck> committeeCheckList = committeeCheckService.getByDate(committeeCheckParam,sysUser);
        return CommonReturnType.create(committeeCheckList);
    }

    @RequestMapping("/getByCheckId")
    @ResponseBody
    public CommonReturnType getByCheckId(@RequestBody CommitteeCheckParam committeeCheckParam, SysUser sysUser)  {
        CommitteeCheckParam committeeCheckList = committeeCheckService.getByCheckId(committeeCheckParam,sysUser);
        return CommonReturnType.create(committeeCheckList);
    }

    @RequestMapping("/deleteByCheckId")
    @ResponseBody
    public CommonReturnType deleteByCheckId(@RequestBody CommitteeCheckParam committeeCheckParam, SysUser sysUser)  {
        committeeCheckService.deleteByCheckId(committeeCheckParam,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/updateSign")
    @ResponseBody
    public CommonReturnType updateSign(@RequestBody CommitteeCheck committeeCheck)  {
        committeeCheckService.updateSign(committeeCheck);
        return CommonReturnType.create(null);
    }
}
