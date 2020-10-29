package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.CaseFiling;
import com.example.upc.dataobject.HikKey;

public interface CaseFilingService {
    PageResult getPage (PageQuery pageQuery);
    void insert(CaseFiling caseFiling);
    void delete(int fpId);
    void update(CaseFiling caseFiling);
    HikKey selectTopOne();
}
