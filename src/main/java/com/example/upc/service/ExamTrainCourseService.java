package com.example.upc.service;

import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.ExamTrainCourseSearchParam;
import com.example.upc.controller.searchParam.ExamTrainMaterialSearchParam;
import com.example.upc.dataobject.ExamTrainCourse;
import com.example.upc.dataobject.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/4/29 21:25
 */
public interface ExamTrainCourseService {
    PageResult<TrainCourseParam> getPage(PageQuery pageQuery, ExamTrainCourseSearchParam examTrainCourseSearchParam);
    void insert(ExamTrainCourse examTrainCourse, List<Integer> materialIds);
    void update(ExamTrainCourse examTrainCourse, List<Integer> materialIds);
    void delete(int id);
    List<ExamCaTrainParam> getCaTrainList(SysUser sysUser);
    Map<String, Object> getCourseMaterialIds(int courseId, int caId);
    Map<String, Object> getCourseIds(int caId);
}
