package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;

public interface PreAndEdService {
    PageResult getPagePreAndEd(PageQuery pageQuery);
    PageResult getPagePre(PageQuery pageQuery,int dept);
    PageResult getPageEd(PageQuery pageQuery,int dept);
}
