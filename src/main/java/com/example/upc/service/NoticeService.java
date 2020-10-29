package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.Notice;

public interface NoticeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(Notice notice);
    void delete(int fpId);
    void update(Notice notice);
}
