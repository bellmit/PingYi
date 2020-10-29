package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.NewsSearchParam;
import com.example.upc.dataobject.SysNews;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SysNewsService;
import com.example.upc.util.StringUtil;
import com.example.upc.util.WeixinUtilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author zcc
 * @date 2019/8/23 13:32
 */
@Controller
@RequestMapping("/sys/news")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysNewsController {
    @Autowired
    private SysNewsService sysNewsService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, SysUser sysUser, NewsSearchParam newsSearchParam){
        return CommonReturnType.create(sysNewsService.getPage(pageQuery,sysUser, newsSearchParam));
    }

    @RequestMapping("/getPageAndroid")
    @ResponseBody
    public CommonReturnType getPageAndroid(PageQuery pageQuery, Integer type, NewsSearchParam newsSearchParam){
        return CommonReturnType.create(sysNewsService.getPageAndroid(pageQuery,type,newsSearchParam));
    }

    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType selectById(@RequestParam("id")int id){
        return CommonReturnType.create(sysNewsService.selectById(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json,SysUser sysUser){
        SysNews sysNews = JSON.parseObject(json,SysNews.class);
        sysNewsService.insert(sysNews,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        SysNews sysNews = JSON.parseObject(json,SysNews.class);
        sysNewsService.update(sysNews);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam("id")int id){
        sysNewsService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/check")
    @ResponseBody
    public CommonReturnType check(@RequestParam("id")int id){
        sysNewsService.check(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/weixin")
    @ResponseBody
    public CommonReturnType selectByEnterpriseId(String ids){
        return CommonReturnType.create(sysNewsService.list(ids));
    }

    @RequestMapping("/weixin1")
    @ResponseBody
    public CommonReturnType weixin1() throws IOException{
        String a = WeixinUtilTest.getAccessToken();
        WeixinUtilTest.newsGo();
        return CommonReturnType.create(a);
    }
}
