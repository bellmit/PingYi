package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.UserParam;
import com.example.upc.controller.searchParam.EnterpriseVoteSearchParam;
import com.example.upc.dataobject.UserEnterpriseVote;
import com.example.upc.service.UserEnterpriseVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/UserEnterpriseVote")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserEnterpriseVoteController {
    @Autowired
    private UserEnterpriseVoteService userEnterpriseVoteService;

    @RequestMapping("/getProblem")
    @ResponseBody
    public CommonReturnType getProblem( ) {
        return CommonReturnType.create(userEnterpriseVoteService.getProblem());
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody EnterpriseVoteSearchParam enterpriseVoteSearchParam) throws Exception {
        return CommonReturnType.create(userEnterpriseVoteService.insert(enterpriseVoteSearchParam));
    }
}
