package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintInformationComeType;

import java.util.List;

public interface ComplaintInformationComeTypeService {
    PageResult getPage (PageQuery pageQuery, ComplaintSearchParam complaintSearchParam);
    void insert(ComplaintInformationComeType complaintInformationComeType);
    void delete(int fpId);
    void update(ComplaintInformationComeType complaintInformationComeType);
    List<ComplaintInformationComeType> getPageList(ComplaintSearchParam complaintSearchParam);
}
