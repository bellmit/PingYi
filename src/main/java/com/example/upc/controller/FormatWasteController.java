package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.FormatWasteParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.InspectionSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.FormatWaste;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.FormatWasteService;
import com.example.upc.service.SysDeptAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/formatwaste")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class FormatWasteController {
    @Autowired
    private FormatWasteService formatWasteService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        WasteSearchParam wasteSearchParam = JSON.parseObject(json,WasteSearchParam.class);
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2)
        {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            wasteSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(formatWasteService.getPage(pageQuery,wasteSearchParam));
        }
        else if (sysUser.getUserType()==1)
        {
            return CommonReturnType.create(formatWasteService.getPageEnterprise(pageQuery, sysUser.getInfoId(), wasteSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(formatWasteService.getPageAdmin(pageQuery, wasteSearchParam));
        }
        else
        {
            formatWasteService.fail();
            return CommonReturnType.create(null);
        }
    }
//    @RequestMapping("/insert")
//    @ResponseBody
//    public CommonReturnType insert(@RequestBody String json){
//        FormatPartyParam formatPartyParam = JSONObject.parseObject(json,FormatPartyParam.class);
//        formatPartyService.insert(formatPartyParam);
//        return CommonReturnType.create(null);
//    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(FormatWasteParam formatWasteParam, SysUser sysUser){
        if (sysUser.getUserType()==1) {
            formatWasteService.insert(formatWasteParam, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatWasteService.fail();
            return CommonReturnType.create(null);
        }
}

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(FormatWasteParam formatWasteParam,SysUser sysUser){
        if (sysUser.getUserType()==1) {
            formatWasteService.update(formatWasteParam, sysUser);
            return CommonReturnType.create(null);
        }
        else {
            formatWasteService.fail();
            return CommonReturnType.create(null);
        }
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        formatWasteService.delete(id);
        return CommonReturnType.create(null);
    }
//    @RequestMapping("/update")
//    @ResponseBody
//    public CommonReturnType update(@RequestBody String json){
//        FormatPartyParam formatPartyParam = JSONObject.parseObject(json,FormatPartyParam.class);
//        formatPartyService.update(formatPartyParam);
//        return CommonReturnType.create(null);
//    }

    @RequestMapping("/standingBook")
    @ResponseBody
    public CommonReturnType standingBook(@RequestBody WasteSearchParam wasteSearchParam, SysUser sysUser) throws IOException {
//        wasteSearchParam.setEnd1(new Date(wasteSearchParam.getEnd1().getTime()-(long) 8 * 60 * 60 * 1000));
        wasteSearchParam.setEnd1(new Date(wasteSearchParam.getEnd1().getTime()+(long) 24 * 60 * 60 * 1000-1));
        return CommonReturnType.create(formatWasteService.standingBook(wasteSearchParam,sysUser));
    }

}
