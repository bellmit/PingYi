package com.example.upc.service;

import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.ExamSubjectSearchParam;
import com.example.upc.dataobject.ExamSubject;
import com.example.upc.dataobject.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/4/29 20:54
 */
public interface ExamSubjectService {
    PageResult<ExamSubjectSearchParam> getPage(PageQuery pageQuery, ExamSubjectSearchParam examSubjectSearchParam);
    void insert(ExamSubject examSubject);
    void update(ExamSubject examSubject);
    void delete(int id);
    List<Integer> getSubjectTopicIds(int id);
    TopicNumParam getNum(ExamSubjectSearchParam examSubjectSearchParam);

    Map<String, Object> getExamTopicList(CaTopicParam caTopicParam);
    List<ExamSubjectParam> getWorkTypeExamList(SysUser sysUser);
    void changeCaExam(int caId,ExamSubject examSubject, List<ExamCaTopic> examCaTopicList);
}
