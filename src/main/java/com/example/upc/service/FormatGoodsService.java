package com.example.upc.service;

import com.example.upc.controller.param.FormatGoodsParam;
import com.example.upc.controller.param.FormatGoodsSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.GoodsSearchParam;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.multipart.MultipartFile;

public interface FormatGoodsService {
    PageResult getPageIn (PageQuery pageQuery, Integer id, GoodsSearchParam goodsSearchParam);
    PageResult getPageInSup (PageQuery pageQuery, GoodsSearchParam goodsSearchParam);
    PageResult getPageInAdmin (PageQuery pageQuery, GoodsSearchParam goodsSearchParam);
    PageResult getPageOut (PageQuery pageQuery, Integer id, GoodsSearchParam goodsSearchParam);
    PageResult getPageOutSup (PageQuery pageQuery, GoodsSearchParam goodsSearchParam);
    PageResult getPageOutAdmin (PageQuery pageQuery, GoodsSearchParam goodsSearchParam);
    void importExcelIn(MultipartFile file, Integer type, SysUser sysUser);
    void importExcelInEx(MultipartFile file, Integer type, SysUser sysUser);
    void importExcelOut(MultipartFile file, Integer type, SysUser sysUser);
    void importExcelOutEx(MultipartFile file, Integer type, SysUser sysUser);
    void insertIn(FormatGoodsParam formatGoodsParam, SysUser sysUser);
    void insertOut(FormatGoodsParam formatGoodsParam, SysUser sysUser);
    void deleteIn(int foId);
    void deleteOut(int foId);
    void updateIn(FormatGoodsParam formatGoodsParam, SysUser sysUser);
    void updateOut(FormatGoodsParam formatGoodsParam, SysUser sysUser);
    void fail();
}
