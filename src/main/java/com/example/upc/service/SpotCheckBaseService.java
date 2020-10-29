package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.EnterpriseRegulatorSearchParam;
import com.example.upc.controller.searchParam.SpotCheckBaseSearchParam;
import com.example.upc.dataobject.SpotCheckBase;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.multipart.MultipartFile;

public interface SpotCheckBaseService {
    PageResult getPage (PageQuery pageQuery, int id, SpotCheckBaseSearchParam spotCheckBaseSearchParam);
    PageResult getUser (PageQuery pageQuery, int id, SpotCheckBaseSearchParam spotCheckBaseSearchParam);
    PageResult getPageAdmin (PageQuery pageQuery, SpotCheckBaseSearchParam spotCheckBaseSearchParam);
    PageResult getPageFoodType (PageQuery pageQuery, String type);
    PageResult getPageByEnterprise (PageQuery pageQuery, String name);
    void insert(SpotCheckBase spotCheckBase, SysUser sysUser);
    void update(SpotCheckBase spotCheckBase);
    void delete(int id);
    void fail();
    void updateRecord(int id);
    PageResult getPageEnterpriseTeam (PageQuery pageQuery, EnterpriseRegulatorSearchParam enterpriseRegulatorSearchParam);
    void importExcel(MultipartFile file, Integer type);
}
