package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintEmergencyType;

import java.util.List;

public interface ComplaintEmergencyTypeService {
    PageResult getPage (PageQuery pageQuery, ComplaintSearchParam complaintSearchParam);
    void insert(ComplaintEmergencyType complaintEmergencyType);
    void delete(int fpId);
    void update(ComplaintEmergencyType complaintEmergencyType);
    List<ComplaintEmergencyType> getPageList(ComplaintSearchParam complaintSearchParam);
}
