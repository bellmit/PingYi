package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SupervisionCreditRewards;
import com.example.upc.service.SupervisionCreditRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/26 20:27
 */
@Controller
@RequestMapping("/supervision/rewards")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionCreditRewardsController {
    @Autowired
    private SupervisionCreditRewardsService supervisionCreditRewardsService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(supervisionCreditRewardsService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(SupervisionCreditRewards supervisionCreditRewards){
        supervisionCreditRewardsService.insert(supervisionCreditRewards);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(SupervisionCreditRewards supervisionCreditRewards){
        supervisionCreditRewardsService.update(supervisionCreditRewards);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionCreditRewardsService.delete(id);
        return CommonReturnType.create(null);
    }
}
