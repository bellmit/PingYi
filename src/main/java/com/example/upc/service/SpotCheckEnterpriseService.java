package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.SpotCheckEnterpriseSearchParam;
import com.example.upc.dataobject.SpotCheckEnterprise;
import com.example.upc.dataobject.SysUser;

public interface SpotCheckEnterpriseService {
    PageResult getPage (PageQuery pageQuery, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam);
    PageResult getPageOk (PageQuery pageQuery);
    PageResult getUser (PageQuery pageQuery, int id, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam);
    PageResult getUserAdmin (PageQuery pageQuery, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam);
    //SpotCheckEnterprise getUser(int id, SpotCheckEnterpriseSearchParam spotCheckEnterpriseSearchParam);
    void insert(SpotCheckEnterprise spotCheckEnterprise, SysUser sysUser);
    void update(SpotCheckEnterprise spotCheckEnterprise);
    void delete(int id);
    void fail();
    void check(int id);
}
