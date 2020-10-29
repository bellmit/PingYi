package com.example.upc.service.impl;

import com.example.upc.controller.param.ExamCaTopic;
import com.example.upc.controller.param.ExamEnquiryParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ExamEnquirySearchParam;
import com.example.upc.dao.ExamCaExamMapper;
import com.example.upc.dataobject.ExamCaExam;
import com.example.upc.dataobject.ExamSubmit;
import com.example.upc.service.ExamCaExamService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 21:52
 */
@Service
public class ExamCaExamServiceImpl implements ExamCaExamService {
    @Autowired
    private ExamCaExamMapper examCaExamMapper;
    @Override
    public PageResult<ExamEnquiryParam> getPage(PageQuery pageQuery, ExamEnquirySearchParam examEnquirySearchParam) {
        int count=examCaExamMapper.countList(examEnquirySearchParam);
        if (count > 0) {
            List<ExamEnquiryParam> examEnquiryParamList = examCaExamMapper.getPage(pageQuery,examEnquirySearchParam);
            PageResult<ExamEnquiryParam> pageResult = new PageResult<>();
            pageResult.setData(examEnquiryParamList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ExamEnquiryParam> pageResult = new PageResult<>();
        return pageResult;
    }
    @Override
    public List<ExamCaExam>getCaExamByCaId(int caId) {
        return (examCaExamMapper.getByCaId(caId));
    }
}
