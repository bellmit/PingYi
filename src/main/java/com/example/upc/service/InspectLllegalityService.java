package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.InspectLllegality;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zcc
 * @date 2019/9/13 10:58
 */
public interface InspectLllegalityService {
    PageResult getPage (PageQuery pageQuery);
    void insert(InspectLllegality inspectLllegality);
    void update(InspectLllegality inspectLllegality);
    void delete(int id);
    void importExcel(MultipartFile file, Integer type);
}
