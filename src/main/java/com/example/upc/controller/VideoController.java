package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.SysDeptAreaService;
import com.example.upc.service.VideoParentService;
import com.example.upc.util.VideoCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/video")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class VideoController {
    @Autowired
    private VideoParentService videoParentService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;


    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        VideoSearchParam videoSearchParam = JSON.parseObject(json,VideoSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            videoSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(videoParentService.getPageSup(pageQuery, videoSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(videoParentService.getPageAdmin(pageQuery, videoSearchParam));
        }
        else
        {
            videoParentService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(int id){
        return CommonReturnType.create(videoParentService.getById(id));
    }

    @RequestMapping("/selectByEnterpriseId")
    @ResponseBody
    public CommonReturnType selectByEnterpriseId(int id){
        return CommonReturnType.create(videoParentService.selectByEnterpriseId(id));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        videoParentService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
//        VideoRecord videoRecord = JSONObject.parseObject(json,VideoRecord.class);
        videoParentService.insert(json,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        videoParentService.update(json,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/cut")
    @ResponseBody
    public CommonReturnType cut(String cmd){
        String filename = System.currentTimeMillis()+".jpg";
        String Cmd = cmd + filename;
        System.out.println(Cmd);
        try{
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(Cmd,null,null);
            InputStream stderr =  proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr,"GBK");
            BufferedReader br = new BufferedReader(isr);
            String line="";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }catch (Exception e){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"截图保存失败");
        }
        return CommonReturnType.create(filename);
    }

}
