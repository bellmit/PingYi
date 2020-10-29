package com.example.upc.dao;

import com.example.upc.dataobject.ExamTrainCourseMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamTrainCourseMaterialMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamTrainCourseMaterial record);
    int insertSelective(ExamTrainCourseMaterial record);
    ExamTrainCourseMaterial selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamTrainCourseMaterial record);
    int updateByPrimaryKey(ExamTrainCourseMaterial record);

    List<Integer> getMaterialIdListByCourseId(@Param("courseId") int courseId);
    void deleteByCourseId(@Param("courseId") int courseId);
    void deleteByMaterialId(@Param("materialId") int materialId);
    void batchInsert(@Param("courseMaterialList") List<ExamTrainCourseMaterial> courseMaterialList);
}