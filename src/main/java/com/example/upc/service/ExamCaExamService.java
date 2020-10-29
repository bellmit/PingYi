package com.example.upc.service;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.controller.param.ExamEnquiryParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ExamEnquirySearchParam;
import com.example.upc.dataobject.ExamCaExam;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 21:52
 */
public interface ExamCaExamService {
    PageResult<ExamEnquiryParam> getPage(PageQuery pageQuery, ExamEnquirySearchParam examEnquirySearchParam);
    List<ExamCaExam>  getCaExamByCaId(int caId);
}
