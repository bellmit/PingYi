package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.AbnormalContent;

import java.util.List;

public interface AbnormalContentService {
    PageResult getPage (PageQuery pageQuery);
    void insert(AbnormalContent abnormalContent);
    void update(AbnormalContent abnormalContent);
    List<AbnormalContent> getList();
}
