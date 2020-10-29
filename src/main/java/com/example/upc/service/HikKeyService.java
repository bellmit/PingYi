package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.HikKey;

import java.util.List;

public interface HikKeyService {
    HikKey selectTopOne();
    PageResult getPage (PageQuery pageQuery);
    void insert(HikKey hikKey);
    void delete(int id);
    void update(HikKey hikKey);
}
