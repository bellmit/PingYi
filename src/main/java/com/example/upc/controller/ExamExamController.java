package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.CaTopicParam;
import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.controller.param.ExamExamParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.ExamExam;
import com.example.upc.dataobject.ExamSubject;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.ExamExamService;
import com.example.upc.service.ExamSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zcc
 * @date 2019/5/10 19:49
 * 考试页面
 */
@Controller
@RequestMapping("/exam/examType")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ExamExamController {
    @Autowired
    private ExamExamService examExamService;
    @Autowired
    private ExamSubjectService examSubjectService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ExamExamParam examExamParam){
        return CommonReturnType.create(examExamService.getPage(pageQuery,examExamParam));
    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(ExamExam examExam){
        System.out.println(examExam.getEndTime());
        examExamService.insert(examExam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(ExamExam examExam){
        examExamService.update(examExam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        examExamService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getCaExamList")
    @ResponseBody
    public CommonReturnType getCaExamList(SysUser sysUser){
        if(sysUser.getUserType()!=3){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非企业用户");
        }
        return CommonReturnType.create(examSubjectService.getWorkTypeExamList(sysUser));
    }

    @RequestMapping("/getCaTopicList")
    @ResponseBody
    public CommonReturnType getCaTopicList(int examCaId,int subjectId,SysUser sysUser){
        if (sysUser==null){
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        if(sysUser.getUserType()!=3){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非企业用户");
        }
        CaTopicParam caTopicParam = new CaTopicParam();
        caTopicParam.setCaId(sysUser.getId());
        caTopicParam.setExamCaId(examCaId);
        caTopicParam.setSubjectId(subjectId);
        return CommonReturnType.create(examSubjectService.getExamTopicList(caTopicParam));
    }
    /*提交考试答题内容*/
    @RequestMapping("/submitCaTopic")
    @ResponseBody
    public CommonReturnType submitCaTopic(@RequestBody String json,SysUser sysUser){
        if (sysUser==null){
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        if(sysUser.getUserType()!=3){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非企业用户");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        List<ExamCaTopic> examCaTopicList = JSONObject.parseArray(jsonObject.getString("list"),ExamCaTopic.class);

        int examId = jsonObject.getInteger("examId");

        ExamSubject examSubject = JSONObject.parseObject(jsonObject.getString("subjectInfo"),ExamSubject.class);
        examSubjectService.changeCaExam(sysUser.getId(),examSubject,examCaTopicList);
        return CommonReturnType.create(null);
    }

}
