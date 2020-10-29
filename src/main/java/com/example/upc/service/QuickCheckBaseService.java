package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.QuickCheckBaseSearchParam;
import com.example.upc.dataobject.QuickCheckBase;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.multipart.MultipartFile;

public interface QuickCheckBaseService {
    PageResult getPage (PageQuery pageQuery, int id, QuickCheckBaseSearchParam quickCheckBaseSearchParam);
    PageResult getUser (PageQuery pageQuery, int id, QuickCheckBaseSearchParam quickCheckBaseSearchParam);
    PageResult getPageAdmin (PageQuery pageQuery, QuickCheckBaseSearchParam quickCheckBaseSearchParam);
    PageResult getPageStatus (PageQuery pageQuery);
    void insert(QuickCheckBase quickCheckBase, SysUser sysUser);
    void update(QuickCheckBase quickCheckBase);
    void delete(int id);
    void fail();
    void updateRecord(int id);
    void importExcel(MultipartFile file, Integer type);
}
