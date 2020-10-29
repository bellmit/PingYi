package com.example.upc.service;

import com.example.upc.controller.param.FormatPictureParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.PictureSearchParam;
import com.example.upc.dataobject.SysUser;

public interface FormatEqupService {
    PageResult getPage (PageQuery pageQuery, PictureSearchParam pictureSearchParam);
    PageResult getPageAdmin (PageQuery pageQuery, PictureSearchParam pictureSearchParam);
    PageResult getPageEnterprise (PageQuery pageQuery, Integer id, PictureSearchParam pictureSearchParam);
    void insert(FormatPictureParam formatPictureParam, SysUser sysUser);
    void update(FormatPictureParam formatPictureParam,SysUser sysUser);
    void delete(int fpId);
    void fail();
}
