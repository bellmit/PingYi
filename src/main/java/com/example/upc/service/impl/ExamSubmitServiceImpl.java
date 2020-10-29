package com.example.upc.service.impl;

import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.dao.ExamCaExamMapper;
import com.example.upc.dao.ExamSubmitMapper;
import com.example.upc.dataobject.ExamSubmit;
import com.example.upc.service.ExamSubmitService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 22:12
 */
@Service
public class ExamSubmitServiceImpl implements ExamSubmitService {
    @Autowired
    private ExamSubmitMapper examSubmitMapper;

    @Override
    @Transactional
    public void changeCaSubmit(int caId, int examId, List<ExamCaTopic> examCaTopicList) {
        examSubmitMapper.deleteByCaIdAndExamId(caId,examId);

        if (CollectionUtils.isEmpty(examCaTopicList)) {
            return;
        }
        List<ExamSubmit> examSubmitList = Lists.newArrayList();
        for (ExamCaTopic examCaTopic : examCaTopicList) {
            ExamSubmit examSubmit = new ExamSubmit();
            examSubmit.setCaId(caId);
            examSubmit.setExamId(examId);
            examSubmit.setTopicId(examCaTopic.getId());
            examSubmit.setAnswer(examCaTopic.getCheck());
            examSubmit.setSubmitTime(new Date());
            examSubmit.setOperateIp("124.124.124");
            examSubmitList.add(examSubmit);
        }
        examSubmitMapper.batchInsert(examSubmitList);
    }
}
