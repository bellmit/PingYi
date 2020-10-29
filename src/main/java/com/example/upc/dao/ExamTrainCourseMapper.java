package com.example.upc.dao;

import com.example.upc.controller.param.ExamCaTrainParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.TrainCaCourseParam;
import com.example.upc.controller.param.TrainCourseParam;
import com.example.upc.controller.searchParam.ExamTrainCourseSearchParam;
import com.example.upc.controller.searchParam.ExamTrainMaterialSearchParam;
import com.example.upc.dataobject.ExamTrainCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamTrainCourseMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamTrainCourse record);
    int insertSelective(ExamTrainCourse record);
    ExamTrainCourse selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamTrainCourse record);
    int updateByPrimaryKeyWithBLOBs(ExamTrainCourse record);
    int updateByPrimaryKey(ExamTrainCourse record);

    int countList();
    List<TrainCourseParam> getPage(@Param("page") PageQuery page,@Param("examTrainMaterialSearch") ExamTrainCourseSearchParam examTrainCourseSearchParam);
    int countByName(@Param("name") String name);
    List<ExamTrainCourse> selectByWorkTypeId(@Param("workTypeId")int workTypeId);
    List<ExamCaTrainParam> getCaTrainList(@Param("workTypeId")int workTypeId,@Param("caId")int caId);
    List<TrainCaCourseParam> getCaListByCaId(@Param("caId")int caId);
}