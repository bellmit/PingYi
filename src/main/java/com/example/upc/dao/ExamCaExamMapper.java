package com.example.upc.dao;

import com.example.upc.controller.param.ExamEnquiryParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ExamEnquirySearchParam;
import com.example.upc.dataobject.ExamCaExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamCaExamMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamCaExam record);
    int insertSelective(ExamCaExam record);
    ExamCaExam selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamCaExam record);
    int updateByPrimaryKey(ExamCaExam record);

    void deleteByCaIdAndExamId(@Param("caId") int caId, @Param("examId") int examId);
    List<ExamCaExam> getByCaId(@Param("caId")int caId);

    int countList(@Param("search") ExamEnquirySearchParam examEnquirySearchParam);
    List<ExamEnquiryParam> getPage(@Param("page") PageQuery page,@Param("search") ExamEnquirySearchParam examEnquirySearchParam);
}