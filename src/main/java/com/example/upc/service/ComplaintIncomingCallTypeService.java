package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintSearchParam;
import com.example.upc.dataobject.ComplaintIncomingCallType;

import java.util.List;

public interface ComplaintIncomingCallTypeService {
    PageResult getPage (PageQuery pageQuery, ComplaintSearchParam complaintSearchParam);
    void insert(ComplaintIncomingCallType complaintIncomingCallType);
    void delete(int fpId);
    void update(ComplaintIncomingCallType complaintIncomingCallType);
    List<ComplaintIncomingCallType> getPageList(ComplaintSearchParam complaintSearchParam);
}
