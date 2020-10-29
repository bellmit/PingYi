package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.ExamTopicBankParam;
import com.example.upc.controller.param.ExamTopicParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ExamTopicSearchParam;
import com.example.upc.dataobject.ExamTopicBank;
import com.example.upc.service.ExamTopicBankService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/5/10 19:53
 */
@Controller
@RequestMapping("/exam/topic")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ExamTopicBankController {
    @Autowired
    private ExamTopicBankService examTopicBankService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ExamTopicSearchParam examTopicSearchParam){
        return CommonReturnType.create(examTopicBankService.getPage(pageQuery,examTopicSearchParam));
    }

    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(){
        return CommonReturnType.create(examTopicBankService.getList());
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        ExamTopicBankParam topicBankParam = JSON.toJavaObject(jsonObject,ExamTopicBankParam.class);
        ExamTopicBank examTopicBank =new ExamTopicBank();
        BeanUtils.copyProperties(topicBankParam,examTopicBank);
        examTopicBank.setScore(topicBankParam.getIndustry());
        examTopicBank.setStatus(topicBankParam.getWorkType());
        examTopicBankService.insert(examTopicBank);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        examTopicBankService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        ExamTopicBankParam topicBankParam = JSON.toJavaObject(jsonObject,ExamTopicBankParam.class);
        ExamTopicBank examTopicBank =new ExamTopicBank();
        BeanUtils.copyProperties(topicBankParam,examTopicBank);
        examTopicBank.setScore(topicBankParam.getIndustry());
        examTopicBank.setStatus(topicBankParam.getWorkType());
        examTopicBankService.update(examTopicBank);
        return CommonReturnType.create(null);
    }
}
