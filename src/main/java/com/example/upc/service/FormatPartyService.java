package com.example.upc.service;

import com.example.upc.controller.param.FormatPartyParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.PartySearchParam;
import com.example.upc.dataobject.FormatParty;
import com.example.upc.dataobject.SysUser;

public interface FormatPartyService {
    PageResult getPage (PageQuery pageQuery, PartySearchParam partySearchParam);
    PageResult getPageAdmin (PageQuery pageQuery, PartySearchParam partySearchParam);
    PageResult getPageEnterprise (PageQuery pageQuery, Integer id, PartySearchParam partySearchParam);
    void insert(String json, SysUser sysUser);
    void delete(int fpId);
    void update(String json, SysUser sysUser);
    FormatPartyParam getById(int id);
    void updateRecord(int id);
    void fail();
}
