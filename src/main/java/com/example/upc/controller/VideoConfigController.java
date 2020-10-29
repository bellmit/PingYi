package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.VideoConfigParam;
import com.example.upc.service.VideoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/9/6 15:46
 */
@Controller
@RequestMapping("/video/config")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class VideoConfigController {
    @Autowired
    private VideoConfigService videoConfigService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(videoConfigService.getPage(pageQuery));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(VideoConfigParam videoConfigParam){
        videoConfigService.insert(videoConfigParam);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(VideoConfigParam videoConfigParam){
        videoConfigService.update(videoConfigParam);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        videoConfigService.delete(id);
        return CommonReturnType.create(null);
    }
}
