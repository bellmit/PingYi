package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.TypeSearchParam;
import com.example.upc.dataobject.FormatOrigin;

import java.util.List;

public interface FormatOriginService {
    PageResult getPage (PageQuery pageQuery, TypeSearchParam typeSearchParam);
    void insert(FormatOrigin formatOrigin);
    void delete(int foId);
    void update(FormatOrigin formatOrigin);
    List<FormatOrigin> getOrigin(PageQuery pageQuery, TypeSearchParam typeSearchParam);
}
