package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.CaTopicParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ExamEnquirySearchParam;
import com.example.upc.controller.searchParam.TrainPersonSearchParam;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcc
 * @date 2019/6/11 17:00
 */
@Controller
@RequestMapping("/exam/enquiry")

@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ExamEnquiryController {
    @Autowired
    private ExamCaExamService examCaExamService;
    @Autowired
    private ExamExamService examExamService;
    @Autowired
    private ExamTrainCaMaterialService examTrainCaMaterialService;
    @Autowired
    private ExamTrainCourseService examTrainCourseService;
    @Autowired
    private ExamSubjectService examSubjectService;
    @Autowired
    private SupervisionCaService supervisionCaService;

    @RequestMapping("/getCaInfo")
    @ResponseBody
    public CommonReturnType getCaInfo(SysUser sysUser){
        int id = sysUser.getId();
        return CommonReturnType.create(supervisionCaService.getCaInfo(id));
    }

    /*在线培训人员*/
    @RequestMapping("/getTrainPage")
    @ResponseBody
    public CommonReturnType getTrainPage(PageQuery pageQuery, TrainPersonSearchParam trainPersonSearchParam){
        return CommonReturnType.create(examTrainCaMaterialService.getPage(pageQuery,trainPersonSearchParam));
    }

    @RequestMapping("/getCaTrainCourseResult")
    @ResponseBody
    public CommonReturnType getCaTrainCourseResult(int caId){
        return CommonReturnType.create(examTrainCourseService.getCourseIds(caId));
    }

    @RequestMapping("/getCaTrainMaterialResult")
    @ResponseBody
    public CommonReturnType getCaTrainMaterialResult(int courseId,int caId){
        return CommonReturnType.create(examTrainCourseService.getCourseMaterialIds(courseId,caId));
    }

    /*在线考试人员*/
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ExamEnquirySearchParam examEnquirySearchParam){
        return CommonReturnType.create(examCaExamService.getPage(pageQuery,examEnquirySearchParam));
    }

    @RequestMapping("/getCaExamResult")
    @ResponseBody
    public CommonReturnType getCaExamResult(int caId){
        return CommonReturnType.create(examCaExamService.getCaExamByCaId(caId));
    }

    @RequestMapping("/getCaTopicResult")
    @ResponseBody
    public CommonReturnType getCaTopicResult(CaTopicParam caTopicParam){
        return CommonReturnType.create(examSubjectService.getExamTopicList(caTopicParam));
    }
}
