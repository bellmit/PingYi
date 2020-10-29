package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.TrainCaMaterialParam;
import com.example.upc.controller.param.TrainMaterialParam;
import com.example.upc.controller.searchParam.ExamTrainMaterialSearchParam;
import com.example.upc.dataobject.ExamTrainMaterial;
import com.example.upc.dataobject.ExamTrainMaterialWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamTrainMaterialMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(ExamTrainMaterialWithBLOBs record);
    int insertSelective(ExamTrainMaterialWithBLOBs record);
    ExamTrainMaterialWithBLOBs selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ExamTrainMaterialWithBLOBs record);
    int updateByPrimaryKeyWithBLOBs(ExamTrainMaterialWithBLOBs record);
    int updateByPrimaryKey(ExamTrainMaterial record);

    int countList();
    int countListByType(@Param("contentType") int contentType);
    List<TrainMaterialParam> getPage(@Param("page") PageQuery page, @Param("examTrainMaterialSearch") ExamTrainMaterialSearchParam examTrainMaterialSearch);
    List<TrainMaterialParam> getPageByType(@Param("page") PageQuery page,@Param("contentType") int contentType);
    int countByName(@Param("name") String name);
    List<TrainMaterialParam> getByIdList(@Param("idList") List<Integer> idList);
    List<TrainCaMaterialParam> getCaTrainList(@Param("caId") int caId,@Param("idList") List<Integer> idList,@Param("courseId") int courseId);
}