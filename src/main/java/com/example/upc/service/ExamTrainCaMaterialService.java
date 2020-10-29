package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.TrainCaMaterialParam;
import com.example.upc.controller.param.TrainPersonParam;
import com.example.upc.controller.searchParam.TrainPersonSearchParam;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 21:56
 */
public interface ExamTrainCaMaterialService {
    void changeCaMaterials(int caId, int courseId,List<TrainCaMaterialParam> caMaterialParamList);
    PageResult<TrainPersonParam> getPage(PageQuery pageQuery, TrainPersonSearchParam trainPersonSearchParam);
}
