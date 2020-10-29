package com.example.upc.service;

import com.example.upc.controller.param.FormatDishesParam;
import com.example.upc.controller.param.FormatDishesSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.DishSearchParam;
import com.example.upc.dataobject.FormatDishes;
import com.example.upc.dataobject.SysUser;

public interface FormatDishesService {
    PageResult<FormatDishesSupParam> getPage (PageQuery pageQuery, DishSearchParam dishSearchParam);
    PageResult getPageEnterprise (PageQuery pageQuery, Integer id, DishSearchParam dishSearchParam);
    PageResult<FormatDishesSupParam> getPageAdmin (PageQuery pageQuery, DishSearchParam dishSearchParam);
    void insert(FormatDishesParam formatDishesParam, SysUser sysUser);
    void update(FormatDishesParam formatDishesParam,SysUser sysUser);
    void delete(int fdId);
    void fail();
}
