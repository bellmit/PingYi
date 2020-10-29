package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintProblemTypeOne;

import java.util.List;

public interface ComplaintProblemTypeOneService {
    PageResult getPage (PageQuery pageQuery, ComplaintSearchParam complaintSearchParam);
    void insert(ComplaintProblemTypeOne complaintProblemTypeOne);
    void delete(int fpId);
    void update(ComplaintProblemTypeOne complaintProblemTypeOne);
    List<ComplaintProblemTypeOne> getPageList(ComplaintSearchParam complaintSearchParam);
}
