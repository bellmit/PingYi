package com.example.upc.service;


import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.TrainMaterialParam;
import com.example.upc.controller.searchParam.ExamTrainMaterialSearchParam;
import com.example.upc.dataobject.ExamTrainMaterialWithBLOBs;

/**
 * @author zcc
 * @date 2019/4/29 21:19
 */
public interface ExamTrainMaterialService {
    PageResult<TrainMaterialParam> getPage(PageQuery pageQuery, ExamTrainMaterialSearchParam examTrainMaterialSearchParam);
    PageResult<TrainMaterialParam> getPageByType(PageQuery pageQuery,int typeId);
    void insert(ExamTrainMaterialWithBLOBs examTrainMaterial);
    void update(ExamTrainMaterialWithBLOBs examTrainMaterial);
    void delete(int id);
}
