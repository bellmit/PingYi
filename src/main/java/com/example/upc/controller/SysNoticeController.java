package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.SysNoticeSearchParam;
import com.example.upc.dataobject.SysNotice;
import com.example.upc.service.SysNoticeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 13:19
 */
@Controller
@RequestMapping("/sys/notice")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(sysNoticeService.getPage(pageQuery));
    }

    @RequestMapping("/getPage2")
    @ResponseBody
    public CommonReturnType getPage2(SysNoticeSearchParam sysNoticeSearchParam){
        List<SysNotice> sysNoticeList = sysNoticeService.getPage2(sysNoticeSearchParam);
        for (SysNotice s:sysNoticeList
             ) {
            s.setEnclosure(s.getEnclosure().equals("[]")||s.getEnclosure().equals("")?"":JSON2ImageUrl(s.getEnclosure()));
        }
        return CommonReturnType.create(sysNoticeList);
    }

    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(@RequestParam("id")int id){
        return CommonReturnType.create(sysNoticeService.getById(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        SysNotice sysNotice = JSON.parseObject(json,SysNotice.class);
        sysNoticeService.insert(sysNotice);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        SysNotice sysNotice = JSON.parseObject(json,SysNotice.class);
        sysNoticeService.update(sysNotice);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam("id")int id){
        sysNoticeService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/check")
    @ResponseBody
    public CommonReturnType check(@RequestParam("id")int id){
        sysNoticeService.check(id);
        return CommonReturnType.create(null);
    }

    /**
     * 将上传的照片JSON格式转换为String图片地址
     * @param jsonObj
     * @return imgUrl
     */
    public  String JSON2ImageUrl(Object jsonObj) {
        JSONArray jsonArray = JSONArray.fromObject(jsonObj);
        JSONObject jsonObject1 = JSONObject.fromObject(jsonArray.get(0));
        JSONObject jsonObject2 = JSONObject.fromObject(jsonObject1.get("response"));
        // 图片存储地址记得上传的时候更改IP
        //String host = "http://127.0.0.1:8080/upload/picture/";
        String host = "http://123.234.130.3:8080/upload/picture/";
        String imgUrl = host+ jsonObject2.get("data");
        return imgUrl;
    }
}
