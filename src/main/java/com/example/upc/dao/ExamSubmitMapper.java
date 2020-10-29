package com.example.upc.dao;

import com.example.upc.dataobject.ExamSubmit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamSubmitMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamSubmit record);
    int insertSelective(ExamSubmit record);
    ExamSubmit selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamSubmit record);
    int updateByPrimaryKey(ExamSubmit record);

    void deleteByCaIdAndExamId(@Param("caId") int caId,@Param("examId") int examId);
    void batchInsert(@Param("caTopicList") List<ExamSubmit> examCaTopicList);
}