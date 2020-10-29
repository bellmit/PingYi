package com.example.upc.dao;

import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.dataobject.ExamSubjectTopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamSubjectTopicMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamSubjectTopic record);
    int insertSelective(ExamSubjectTopic record);
    ExamSubjectTopic selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamSubjectTopic record);
    int updateByPrimaryKey(ExamSubjectTopic record);

    List<Integer> getTopicIdListBySubjectId(@Param("subjectId") int subjectId);
    void deleteBySubjectId(@Param("subjectId") int subjectId);
    void deleteByTopicId(@Param("topicId") int topicId);
    void batchInsert(@Param("subjectTopicList") List<ExamSubjectTopic> subjectTopicList);
    List <ExamSubjectTopic> getList();
}