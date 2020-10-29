package com.example.upc.service;

import com.example.upc.controller.param.*;
import com.example.upc.dataobject.ExamCaExam;
import com.example.upc.dataobject.ExamExam;
import com.example.upc.dataobject.ExamSubject;
import com.example.upc.dataobject.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/4/29 20:42
 */
public interface ExamExamService {
    PageResult<ExamExamParam> getPage(PageQuery pageQuery,ExamExamParam examExamParam);
    void insert(ExamExam examExam);
    void update(ExamExam examExam);
    void delete(int id);
    Map<String, Object> getExamTopicList(CaTopicParam caTopicParam);
    List<ExamCaExamParam> getWorkTypeExamList(SysUser sysUser);
    void changeCaExam(int caId, int examId,ExamSubject examSubject, List<ExamCaTopic> examCaTopicList);
}
