package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatPartyParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.PartySearchParam;
import com.example.upc.dataobject.FormatParty;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatPartyService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatparty")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatPartyController {
    @Autowired
    private FormatPartyService formatPartyService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        PartySearchParam partySearchParam = JSON.parseObject(json,PartySearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            partySearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatPartyService.getPage(pageQuery,partySearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatPartyService.getPageEnterprise(pageQuery, sysUser.getInfoId(), partySearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatPartyService.getPageAdmin(pageQuery, partySearchParam));
        }
        else
        {
            formatPartyService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(int id){
        return CommonReturnType.create(formatPartyService.getById(id));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatPartyService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1) {
            formatPartyService.insert(json, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatPartyService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json, SysUser sysUser){
        if (sysUser.getUserType()==1){
        formatPartyService.update(json,sysUser);
        return CommonReturnType.create(null);
        }
         else {
        formatPartyService.fail();
        return CommonReturnType.create(null);
    }
    }
    @RequestMapping("/updateRecord")
    @ResponseBody
    public CommonReturnType updateRecord(int id){
        formatPartyService.updateRecord(id);
        return CommonReturnType.create(null);
    }
}
