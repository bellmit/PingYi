package com.example.upc.service;

import com.example.upc.controller.param.CaTopicParam;
import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.controller.searchParam.ExamSubjectSearchParam;
import com.example.upc.dataobject.ExamSubject;
import com.example.upc.dataobject.ExamTopicBank;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 22:10
 */
public interface ExamSubjectTopicService {
    List<ExamTopicBank> getListBySubject(int SubjectId);
    List<ExamCaTopic> getCaListBySubject(CaTopicParam caTopicParam);
    void changeSubjectTopics(ExamSubject examSubject, List<ExamTopicBank> examTopicBankList);
    void deleteBySubjectId(int id);
}
