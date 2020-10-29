package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ExamTopicSearchParam;
import com.example.upc.dataobject.ExamTopicBank;

import java.util.List;

/**
 * @author zcc
 * @date 2019/4/29 20:15
 */
public interface ExamTopicBankService {
    PageResult<ExamTopicBank> getPage(PageQuery pageQuery, ExamTopicSearchParam examTopicSearchParam);
    List<ExamTopicBank> getList();
    void insert(ExamTopicBank examTopicBank);
    void update(ExamTopicBank examTopicBank);
    void delete(int id);
}
