package com.example.upc.service;

import com.example.upc.controller.param.FormatPictureParam;
import com.example.upc.controller.param.FormatPictureSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.PictureSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface FormatPictureService {
    PageResult getPage (PageQuery pageQuery, PictureSearchParam pictureSearchParam);
    PageResult getPageAdmin (PageQuery pageQuery, PictureSearchParam pictureSearchParam);
    PageResult getPageEnterprise (PageQuery pageQuery, Integer id, PictureSearchParam pictureSearchParam);
    PageResult getPageByEnterpriseId (PageQuery pageQuery, int id);
    void insert(FormatPictureParam formatPictureParam, SysUser sysUser);
    void update(FormatPictureParam formatPictureParam,SysUser sysUser);
    void delete(int fpId);
    void fail();
    List<FormatPictureSupParam> getPageByEnterpriseId2(WasteSearchParam wasteSearchParam , int id);
}
