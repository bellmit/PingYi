package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.TrainPersonParam;
import com.example.upc.controller.searchParam.TrainPersonSearchParam;
import com.example.upc.dataobject.ExamTrainCaMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamTrainCaMaterialMapper {

    void deleteByCaId(@Param("caId") int caId,@Param("courseId") int courseId);
    void batchInsert(@Param("caMaterialList") List<ExamTrainCaMaterial> examTrainCaMaterialList);

    int countList(@Param("search") TrainPersonSearchParam trainPersonSearchParam);
    List<TrainPersonParam> getPage(@Param("page") PageQuery page,@Param("search") TrainPersonSearchParam trainPersonSearchParam);
    int selectByCaId(@Param("caId") int caId);
}