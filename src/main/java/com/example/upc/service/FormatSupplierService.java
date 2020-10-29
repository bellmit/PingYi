package com.example.upc.service;

import com.example.upc.controller.param.FormatSupplierParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.SupplierSearchParam;
import com.example.upc.dataobject.FormatSupplier;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface FormatSupplierService {
    PageResult getPage (PageQuery pageQuery, SupplierSearchParam supplierSearchParam, SysUser sysUser);
    PageResult getPageSup (PageQuery pageQuery, SupplierSearchParam supplierSearchParam);
    PageResult getPageAdmin (PageQuery pageQuery, SupplierSearchParam supplierSearchParam);
    void insert(FormatSupplierParam formatSupplierParam, SysUser sysUser);
    void delete(int fsId);
    void update(FormatSupplierParam formatSupplierParam, SysUser sysUser);
    FormatSupplier selectByName(String name);
    FormatSupplier selectById(Integer id, SysUser sysUser);
    void fail();
    List<FormatSupplier> getPage2 ( SupplierSearchParam supplierSearchParam, SysUser sysUser);
}
