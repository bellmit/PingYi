package com.example.upc.dao;

import com.example.upc.controller.param.ExamCaExamParam;
import com.example.upc.controller.param.ExamExamParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.ExamExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamExamMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamExam record);
    int insertSelective(ExamExam record);
    ExamExam selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamExam record);
    int updateByPrimaryKeyWithBLOBs(ExamExam record);
    int updateByPrimaryKey(ExamExam record);

    int countList();
    List<ExamExamParam> getPage(@Param("page") PageQuery page,@Param("search") ExamExamParam examExamParam);
    int countByName(@Param("name") String name);
    List<ExamCaExamParam> getByWorkType(@Param("workType") int workType);
}