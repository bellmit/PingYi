package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.TrainCaMaterialParam;
import com.example.upc.controller.searchParam.ExamTrainCourseSearchParam;
import com.example.upc.dataobject.ExamTrainCourse;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.ExamTrainCaMaterialService;
import com.example.upc.service.ExamTrainCourseMaterialService;
import com.example.upc.service.ExamTrainCourseService;
import com.example.upc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zcc
 * @date 2019/5/10 19:54
 */
@Controller
@RequestMapping("/exam/trainCourse")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ExamTrainCourseController {
    @Autowired
    private ExamTrainCourseService examTrainCourseService;
    @Autowired
    private ExamTrainCourseMaterialService examTrainCourseMaterialService;
    @Autowired
    private ExamTrainCaMaterialService examTrainCaMaterialService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, ExamTrainCourseSearchParam examTrainCourseSearchParam){
        return CommonReturnType.create(examTrainCourseService.getPage(pageQuery, examTrainCourseSearchParam));
    }

    @RequestMapping("/getCourseMaterialIds")
    @ResponseBody
    public CommonReturnType getCourseMaterialIds(int id){
        return CommonReturnType.create(examTrainCourseMaterialService.getListByCourseId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        List<Integer> topicIdList = StringUtil.splitToListInt(jsonObject.getString("materialIds"));
        ExamTrainCourse examTrainCourse = JSON.toJavaObject(jsonObject,ExamTrainCourse.class);
        examTrainCourseService.insert(examTrainCourse,topicIdList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        examTrainCourseService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        List<Integer> materialIdList = StringUtil.splitToListInt(jsonObject.getString("materialIds"));
        ExamTrainCourse examTrainCourse = JSON.toJavaObject(jsonObject,ExamTrainCourse.class);
        examTrainCourseService.update(examTrainCourse,materialIdList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getCaTrainList")
    @ResponseBody
    public CommonReturnType getCaTrainList(SysUser sysUser){
        if (sysUser==null){
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        if(sysUser.getUserType()!=3){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非企业用户");
        }
        return CommonReturnType.create(examTrainCourseService.getCaTrainList(sysUser));
    }


    @RequestMapping("/getCaTrain")
    @ResponseBody
    public CommonReturnType getCaTrain(int courseId, SysUser sysUser){
        if (sysUser==null){
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        if(sysUser.getUserType()!=3){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非企业用户");
        }
        return CommonReturnType.create(examTrainCourseService.getCourseMaterialIds(courseId,sysUser.getId()));
    }

    @RequestMapping("/submitCaTrain")
    @ResponseBody
    public CommonReturnType submitCaTrain(@RequestBody String json,SysUser sysUser){
        if (sysUser==null){
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        if(sysUser.getUserType()!=3){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非企业用户");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        List<TrainCaMaterialParam> trainCaMaterialParamList = JSONObject.parseArray(jsonObject.getString("list"),TrainCaMaterialParam.class);
        examTrainCaMaterialService.changeCaMaterials(sysUser.getId(),jsonObject.getInteger("courseId"),trainCaMaterialParamList);
        return CommonReturnType.create(null);
    }
}
