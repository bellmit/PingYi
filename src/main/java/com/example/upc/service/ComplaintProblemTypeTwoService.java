package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintProblemTypeTwo;

import java.util.List;

public interface ComplaintProblemTypeTwoService {
    PageResult getPage (PageQuery pageQuery, ComplaintSearchParam complaintSearchParam);
    void insert(ComplaintProblemTypeTwo complaintProblemTypeTwo);
    void delete(int fpId);
    void update(ComplaintProblemTypeTwo complaintProblemTypeTwo);
    List<ComplaintProblemTypeTwo> getListByOne(String One);
    List<ComplaintProblemTypeTwo> getPageList(ComplaintSearchParam complaintSearchParam);
}
