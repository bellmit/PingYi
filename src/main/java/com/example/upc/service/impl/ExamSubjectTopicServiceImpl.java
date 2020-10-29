package com.example.upc.service.impl;

import com.example.upc.controller.param.CaTopicParam;
import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.controller.searchParam.ExamSubjectSearchParam;
import com.example.upc.dao.ExamSubjectTopicMapper;
import com.example.upc.dao.ExamTopicBankMapper;
import com.example.upc.dataobject.ExamSubject;
import com.example.upc.dataobject.ExamSubjectTopic;
import com.example.upc.dataobject.ExamTopicBank;
import com.example.upc.service.ExamSubjectTopicService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 22:11
 */
@Service
public class ExamSubjectTopicServiceImpl implements ExamSubjectTopicService {
    @Autowired
    private ExamSubjectTopicMapper examSubjectTopicMapper;
    @Autowired
    private ExamTopicBankMapper examTopicBankMapper;

    @Override
    public List<ExamTopicBank> getListBySubject(int SubjectId) {
        List<Integer> topicIdList = examSubjectTopicMapper.getTopicIdListBySubjectId(SubjectId);
        if (CollectionUtils.isEmpty(topicIdList)) {
            return Lists.newArrayList();
        }
        return examTopicBankMapper.getByIdList(topicIdList);
    }

    @Override
    public List<ExamCaTopic> getCaListBySubject(CaTopicParam caTopicParam) {
        int subjectId = caTopicParam.getSubjectId();
        List<Integer> topicIdList = examSubjectTopicMapper.getTopicIdListBySubjectId(subjectId);
        if (CollectionUtils.isEmpty(topicIdList)) {
            return Lists.newArrayList();
        }
        return examTopicBankMapper.getCaExamList(caTopicParam,topicIdList);
    }

    @Override
    public void changeSubjectTopics(ExamSubject examSubject, List<ExamTopicBank> examTopicBankList) {

//        List<Integer> originTopicIdList = examSubjectTopicMapper.getTopicIdListBySubjectId(subjectId);

        updateRoleUsers(examSubject, examTopicBankList);
    }

    @Override
    public void deleteBySubjectId(int id) {
        examSubjectTopicMapper.deleteBySubjectId(id);
    }

    @Transactional
    public void updateRoleUsers(ExamSubject examSubject, List<ExamTopicBank> examTopicBankList) {

        if (CollectionUtils.isEmpty(examTopicBankList)) {
            return;
        }

        List<ExamSubjectTopic> subjectTopicList = Lists.newArrayList();
        for (ExamTopicBank examTopicBank : examTopicBankList) {
            ExamSubjectTopic examSubjectTopic = new ExamSubjectTopic();
            examSubjectTopic.setSubjectId(examSubject.getId());
            examSubjectTopic.setTopicId(examTopicBank.getId());
            switch (examTopicBank.getType()){
                case 1:
                    examSubjectTopic.setScore(examSubject.getJudgementScore());
                    break;
                case 2:
                    examSubjectTopic.setScore(examSubject.getSingleScore());
                    break;
                case 3:
                    examSubjectTopic.setScore(examSubject.getMultipleScore());
                    break;
            }
            examSubjectTopic.setOperator("操作人");
            examSubjectTopic.setOperateIp("124.124.124");
            examSubjectTopic.setOperateTime(new Date());
            subjectTopicList.add(examSubjectTopic);
        }
        examSubjectTopicMapper.batchInsert(subjectTopicList);
    }
}
