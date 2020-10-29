package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.QuickCheckEnterpriseSearchParam;
import com.example.upc.dataobject.QuickCheckEnterprise;
import com.example.upc.dataobject.SysUser;

public interface QuickCheckEnterpriseService {
    PageResult getPage (PageQuery pageQuery, QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam);
    PageResult getUser (PageQuery pageQuery, int id, QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam);
    PageResult getPageOk (PageQuery pageQuery);
//    QuickCheckEnterprise getUser(int id, QuickCheckEnterpriseSearchParam quickCheckEnterpriseSearchParam);
    void insert(QuickCheckEnterprise quickCheckEnterprise, SysUser sysUser);
    void update(QuickCheckEnterprise quickCheckEnterprise);
    void delete(int id);
    void fail();
    void check(int id);
}
