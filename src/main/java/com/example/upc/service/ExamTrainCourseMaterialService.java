package com.example.upc.service;

import com.example.upc.controller.param.TrainCaMaterialParam;
import com.example.upc.controller.param.TrainMaterialParam;


import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 22:00
 */
public interface ExamTrainCourseMaterialService {
    List<TrainMaterialParam> getListByCourseId(int courseId);
    List<TrainCaMaterialParam> getCaListByCourseId(int caId,int courseId);
    void changeCourseMaterials(int courseId,List<Integer> materialIdList);
}
