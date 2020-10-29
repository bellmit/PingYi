package com.example.upc.service;

import com.example.upc.controller.param.ComplaintRecordParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintEnterpriseSearchParam;
import com.example.upc.controller.searchParam.ComplaintRecordSearchParam;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.dataobject.ComplaintRecord;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface ComplaintRecordService {
    PageResult<ComplaintRecordParam> getPage (PageQuery pageQuery, SysUser sysUser, ComplaintRecordSearchParam complaintRecordSearchParam);
    PageResult<ComplaintRecordParam> getPageAdmin (PageQuery pageQuery, ComplaintRecordSearchParam complaintRecordSearchParam);
    void insert(ComplaintRecord complaintRecord, SysUser sysUser);
    void delete(int fpId);
    void update(ComplaintRecord complaintRecord, SysUser sysUser);
    PageResult getPageEnterprise(PageQuery pageQuery, ComplaintEnterpriseSearchParam complaintEnterpriseSearchParam);
    ComplaintRecord getRecordById(Integer id);
    void fail();
}
